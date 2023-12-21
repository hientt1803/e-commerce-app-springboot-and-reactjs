/**
 * 
 */
var app = angular.module('myApp_Update_Account', []);
app.controller('myCtrl_Update_Account', function($scope, $http) {
	$scope.items = [];
	$scope.form = {};

	// Load account
	$scope.initialize = function() {
		$http.get("/rest/update-account").then(resp => {
			$scope.photo = "/img/update-account/" + resp.data.photo;
			$scope.form = angular.copy(resp.data);
		})
	}

	$scope.initialize();

	var image;

	// Upload hình
	$scope.imageChanged = function(files) {
		image = files[0];
		var file = files[0];
		var reader = new FileReader();

		reader.onload = function(e) {
			$scope.$apply(function() {
				$scope.photo = e.target.result; // Lưu đường dẫn hình ảnh vào $scope.photo
			});
		};
		$scope.form.photo = file.name;
		reader.readAsDataURL(file);
	}

	$scope.validate = function() {
		var item = angular.copy($scope.form);
		if (item.fullname.trim() === '') {
			alert('Vui lòng nhập Họ và Tên');
			return false;
		}
		if (item.email.trim() === '') {
			alert('Vui lòng nhập Email');
			return false;
		}
		const emailPattern = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/;
		if (!emailPattern.test(item.email)) {
			alert('Email không hợp lệ');
			return false;
		}
		return true;
	}

	// Cập nhật sản phẩm
	$scope.update = function() {
		if ($scope.validate() == true) {
			var item = angular.copy($scope.form);
			var formData = new FormData();
			formData.append('file', image);

			// lưu vào database
			$http.put("/rest/update-account/${item.username}", item).then(resp => {
				var index = $scope.items.findIndex(p => p.username === item.username);
				$scope.items[index] = item;
				alert("Cập nhật tài khoản thành công!!");
			})
				.catch(error => {
					alert("Lỗi cập nhật tài khoản!!");
					console.log("Error", error);
				});

			// lưu ảnh vào thư mục
			$http.post("/rest/upload/update-account", formData, {
				transformRequest: angular.identity,
				headers: { "Content-Type": undefined }
			}).then(resp => {
			}).catch(error => {
				console.log("Error", error);
			})
		}
	}
});