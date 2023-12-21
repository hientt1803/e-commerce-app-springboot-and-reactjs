/**
 *
 */
var app = angular.module('myApp_Checkout', []);
app.controller('myCtrl_Checkout', function($scope, $http, $location) {
	$scope.items = [];
	$scope.discount = 0;

	$scope.initialize = function() {
		var json = localStorage.getItem('selectedItems');
		$scope.items = json ? JSON.parse(json) : [];

		$scope.total = 0;
		angular.forEach($scope.items, function(item) {
			$scope.total += item.quantity * item.price;
		});
	};

	$scope.initialize();

	$scope.saveCoupon = function() {
		var couponId = $scope.couponId;
		if (!couponId || couponId.trim() === '') {
			alert('Vui lòng nhập mã giảm giá !!!');
			return;
		}
		$http
			.post('/rest/coupon?couponId=' + couponId)
			.then(function(response) {
				var expirationDate = new Date(response.data.expirationDate);
				var startDate = new Date(response.data.startDate);
				if (expirationDate > new Date() && startDate < new Date()) {
					$scope.discount = (response.data.discountAmount / 100) * $scope.total;
					$scope.order.coupon.couponCode = $scope.couponId;
				} else {
					alert('Mã giảm giá không hợp lệ !!!');
				}
			})
			.catch(function(error) {
				console.log(error);
				alert('Mã giảm giá không hợp lệ !!!');
			});
	};

	var usernameInput = document.getElementById('usernameInput');
	var totalPrice = parseFloat($scope.total) - parseFloat($scope.discount);
	$scope.order = {
		account: { username: usernameInput.getAttribute('value') },
		phone: '',
		address: '',
		totalPrice: totalPrice,
		coupon: { couponCode: null },
		status: 'XL',
		payment: { idPaymemt: $('.form-check-input').text() },
		get orderDetails() {
			return $scope.items.map((item) => {
				return {
					product: { id: item.id },
					price: item.price,
					quantity: item.quantity,
				};
			});
		},
		//		clear() {
		//			if (confirm('Bạn có chắc muốn xóa toàn bộ sản phẩm khỏi giỏ hàng ?')) {
		//				this.items = [];
		//				this.selectedItems = [];
		//				this.saveToLocalStorage();
		//			}
		//		},
		purchase() {
			var order = angular.copy(this);

			if (order.phone.trim() === '') {
				alert('Vui lòng nhập số điên thoại');
				return;
			}

			if (order.address.trim() === '') {
				alert('Vui lòng nhập địa chỉ');
				return;
			}

			const phoneNumberPattern = /^(?:\+?84|0)(?:\d{9}|\d{10})$/;
			if (!phoneNumberPattern.test(order.phone)) {
				alert('Số điện thoại không hợp lệ');
				return;
			}

			// Thực hiện đặt hàng
			$http
				.post('/rest/orders', order)
				.then((resp) => {
					if (order.payment === '2') {
						alert("Thanh toán thành công !!!");
						window.location.href = "/shop/order-history";
					}
				})
				.catch((error) => {
					alert('Thanh toán lỗi !!!');
					console.log(error);
				});
		},
	};
});
