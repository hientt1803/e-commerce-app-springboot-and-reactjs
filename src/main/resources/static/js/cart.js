const app = angular.module('app', []);
app.controller('cartCtrl', function ($scope, $http) {
	$scope.cart = {
		items: [],
		currentPath: '',
		checkAll: false,
		selectedItems: [],
		quantityInDetail: 1,
		isQuantityValid: true,
		isOutOfProduct: false,
		amoutInDetail: 1,
		storeAmount: 0,
		plusInDetail() {
			this.quantityInDetail++;
		},
		minusInDetail() {
			if (this.quantityInDetail === 1) {
				return;
			}
			this.quantityInDetail--;
		},
		plus(id) {
			console.log(this.storeAmount);
			let data;
			if (this.storeAmount == 0) {
				$http.get(`/rest/products/${id}`).then((resp) => {
					console.log(resp.data);
					this.storeAmount = resp.data.quantity;
					data = resp.data;
				});
				return;
			}

			var item = this.items.find((item) => item.id == id);
			if (item.quantity >= this.storeAmount) {
				alert('Bạn đã chọn vượt quá số lượng sản phẩm khả dụng !');
				item.quantity = this.storeAmount;
				return;
			}
			if (item) {
				item.quantity++;
			} else {
				this.items.push(data);
			}

			this.saveToLocalStorage();
		},
		minus(id) {
			var item = this.items.find((item) => item.id == id);
			if (item.quantity == 1) {
				return;
			}
			item.quantity--;
			this.saveToLocalStorage();
		},
		checkQuantity(totalProduct) {
			console.log(totalProduct);
			if (this.quantityInDetail > totalProduct) {
				// alert('Bạn không được chọn số lượng sản phẩm quá số lượng có sẵn !!');
				// this.quantityInDetail = 1;
				this.isQuantityValid = false;
			} else {
				this.isQuantityValid = true;
			}
		},
		addInDetail(id) {
			var item = this.items.find((item) => item.id == id);
			if (item) {
				item.quantity += this.quantityInDetail;
				this.saveToLocalStorage();
				this.quantityInDetail = 1;
			} else {
				$http.get(`../rest/products/${id}`).then((resp) => {
					console.log(resp.data);
					resp.data.quantity = this.quantityInDetail;

					this.items.push(resp.data);
					this.saveToLocalStorage();
					this.quantityInDetail = 1;
				});
			}
		},
		add(id) {
			var item = this.items.find((item) => item.id == id);
			if (item) {
				item.quantity++;
				this.saveToLocalStorage();
			} else {
				$http.get(`rest/products/${id}`).then((resp) => {
					console.log(resp.data);
					resp.data.quantity = 1;
					this.items.push(resp.data);
					this.saveToLocalStorage();
				});
			}
		},
		//set active page
		get path() {
			let path = '';
			if (window.location.href.includes('shop')) {
				path = 'shop';
			}
			if (window.location.href.includes('cart-detail') || window.location.href.includes('order-history')) {
				path = 'order';
			}
			return path;
		},
		setSelected(id) {
			let itemFound = this.items.find((item) => item.id == id);
			let index = this.selectedItems.findIndex((item) => item.id == id);
			if (index != -1) {
				this.selectedItems.splice(index, 1);
			} else {
				this.selectedItems.push(itemFound);
			}
		},
		remove(id) {
			console.log(id);
			var index = this.items.findIndex((item) => item.id == id);
			let selectedIndex = this.selectedItems.findIndex((item) => item.id == id);
			console.log(this.selectedItems);
			console.log(index);
			console.log(selectedIndex);
			if (confirm('Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng ?')) {
				this.items.splice(index, 1);
				this.selectedItems.splice(selectedIndex, 1);
				this.saveToLocalStorage();
			}
		},
		selectAll() {
			this.checkAll = !this.checkAll;

			if (this.checkAll) {
				this.selectedItems = [...this.items];
				$scope.check = true;
			} else {
				this.selectedItems = [];
				$scope.check = false;
			}
		},
		get countSelectedItems() {
			return this.selectedItems.map((item) => item.quantity).reduce((total, quantity) => (total += quantity), 0);
		},
		get count() {
			return this.selectedItems.map((item) => item.quantity).reduce((total, quantity) => (total += quantity), 0);
		},
		get countTotal() {
			return this.items.map((item) => item.quantity).reduce((total, quantity) => (total += quantity), 0);
		},
		get amount() {
			return this.selectedItems.map((item) => item.quantity * item.price).reduce((total, quantity) => (total += quantity), 0);
		},
		clear() {
			if (confirm('Bạn có chắc muốn xóa toàn bộ sản phẩm khỏi giỏ hàng ?')) {
				this.items = [];
				this.selectedItems = [];
				this.saveToLocalStorage();
			}
		},
		amt_of(items) {},
		saveToLocalStorage() {
			var cart = JSON.stringify(angular.copy(this.items));
			localStorage.setItem('cart', cart);
			var selectedItems = JSON.stringify(angular.copy(this.selectedItems));
			localStorage.setItem('selectedItems', selectedItems);
		},
		loadFormLocalStorage() {
			var json = localStorage.getItem('cart');
			this.items = json ? JSON.parse(json) : [];
			// this.selectedItems = [];
		},
		order() {
			this.items = this.items.filter((item) => !this.selectedItems.includes(item));
			this.saveToLocalStorage();
		},
	};
	$scope.cart.loadFormLocalStorage();
});
