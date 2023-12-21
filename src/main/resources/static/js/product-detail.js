/**
 * 
 */

let star = document.querySelectorAll('input');
let showValue = document.querySelector('#rating-value');

for (let i = 0; i < star.length; i++) {
	star[i].addEventListener('click', function() {
		i = this.value;
		showValue.value = i;
	});
}

const decreaseBtn = document.querySelector('.decrease');
const increaseBtn = document.querySelector('.increase');
const quantityInput = document.getElementById('quantity');
let maxQuantity = parseInt(document.getElementById("maxQuantity").value);

decreaseBtn.addEventListener('click', () => {
	let value = parseInt(quantityInput.value);
	value = isNaN(value) ? 0 : value;
	value--;
	if (value < 1) {
		value = 1;
	}
	quantityInput.value = value;
});

increaseBtn.addEventListener('click', () => {
	let value = parseInt(quantityInput.value);
	value = isNaN(value) ? 0 : value;
	value++;
	if (value > maxQuantity) {
		alert("Số lượng tối đa cho sản phẩm này là " + maxQuantity);
	} else {
		quantityInput.value = value;
	}

});

quantityInput.addEventListener('change', function() {
	// Lấy số lượng sản phẩm
	var quantity = parseInt(quantityInput.value);

	// Kiểm tra nếu số lượng sản phẩm lớn hơn số lượng hàng có sẵn
	if (quantity > maxQuantity) {
		alert("Số lượng tối đa cho sản phẩm này là " + maxQuantity);
		quantityInput.value = maxQuantity;
	} else if (quantity <= 0) {
		alert("Số lượng phải lớn hơn 0 !!!");
		quantityInput.value = 1;
	}
});

quantityInput.addEventListener('keypress', function(event) {
	// Lấy mã ASCII của phím được nhấn
	var keycode = event.keyCode || event.which;

	// Kiểm tra nếu phím được nhấn là phím enter
	if (keycode === 13) {
		// Ngăn chặn submit mặc định của trình duyệt
		event.preventDefault();
	}
});

let currentQuantity = parseInt(document.getElementById("quantity").value);
if (currentQuantity >= maxQuantity) {
	alert("Số lượng tối đa cho sản phẩm này là " + maxQuantity);
	document.getElementById("quantity").value = 1;
}