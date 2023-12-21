import {faPenSquare} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {DateRangePicker} from '@mui/x-date-pickers-pro/DateRangePicker';
import {AdapterDayjs} from '@mui/x-date-pickers/AdapterDayjs';
import {LocalizationProvider} from '@mui/x-date-pickers/LocalizationProvider';
import {DemoItem} from '@mui/x-date-pickers/internals/demo';
import {Button, Card, Col, Form, Row, Toast} from '@themesberg/react-bootstrap';
import dayjs from 'dayjs';
import moment from 'moment-timezone';
import React, {useEffect, useState} from 'react';
import DataTable from 'react-data-table-component';
const ROOT_URL = 'http://localhost:8080/admin/coupons';

export default () => {
	const [showCard, setShowCard] = useState(false);
	const [showAddBtn, setShowAddBtn] = useState(true);
	const [showToast, setShowToast] = useState(false);
	const [showUpdateBtn, setShowUpdateBtn] = useState(false);
	const [showNote, setShowNote] = useState(false);
	const handleCloseModal = () => setShowModal(false);
	const [loading, setLoading] = useState(true);
	const [newStatus, setNewStatus] = useState('');
	const [filterKey, setFilterKey] = useState('');
	const [notes, setNotes] = useState('');
	const [showModal, setShowModal] = useState(false);
	const [coupons, setCoupons] = useState([{}]);
	const [couponsFilter, setCouponsFilter] = useState([{}]);
	const [update, setUpdate] = useState(false);
	const [focusedInput, setFocusedInput] = useState(null);

	//form
	const [couponCode, setCouponCode] = useState('');
	const [couponName, setCouponName] = useState('');
	const [createdDate, setCreatedDate] = useState('');
	const [discountAmount, setDiscountAmount] = useState(0);
	const [startDate, setStartDate] = useState(new Date());
	const [expirationDate, setExpirationDate] = useState('');
	const [couponCodeError, setCouponCodeError] = useState();
	const [couponNameError, setCouponNameError] = useState();
	const [discountAmountError, setDiscountAmountError] = useState();
	const [activated, setActivated] = useState(true);
	const [formValid, setFormValid] = useState(false);
	const [rangeDate, setRangeDate] = useState([dayjs(new Date()), dayjs(new Date())]);

	const [coupon, setCoupon] = useState({
		couponCode: '',
		discountAmount: 0,
		expirationDate: '',
		startDate: '',
		activated: true,
		couponName: '',
		createdDate: '',
	});

	const columns = [
		{
			name: 'Mã giảm giá',
			selector: (row) => row.couponCode,
		},
		{
			name: 'Tên khuyến mãi',
			selector: (row) => row.couponName,
			sortable: true,
		},
		{
			name: 'Phần trăm giảm',
			sortable: true,
			selector: (row) => row.discountAmount,
		},

		{
			name: 'Ngày tạo',
			selector: (row) => row.createdDate,
			sortable: true,
		},
		{
			name: 'Ngày bắt đầu',
			selector: (row) => row.startDate,
			sortable: true,
		},
		{
			name: 'Ngày hết hạn',
			selector: (row) => row.expirationDate,
			sortable: true,
		},
		{
			name: 'Trạng thái',
			selector: (row) => displayStatus(row.activated),
			sortable: true,
		},
		{
			button: true,
			name: 'Thao tác',
			cell: (row) => (
				<button
					className='btn btn-warning btn-xs'
					onClick={() => {
						console.log(row);
						handleGetCouponDetails(row);
					}}>
					<FontAwesomeIcon
						icon={faPenSquare}
						size='lg'
						className='text-white'
					/>
				</button>
			),
		},
	];
	const conditionalRowStyles = [
		{
			when: (row) => row.activated === false,

			style: (row) => ({backgroundColor: row.activated ? 'inerit' : 'pink'}),
		},
		{
			when: (row) => row.couponCode === coupon.couponCode,

			style: (row) => ({border: '1px grey solid'}, {backgroundColor: 'rgb(109, 235, 198)'}),
		},
	];
	const getData = async () => {
		try {
			const resp = await fetch(ROOT_URL);
			const data = await resp.json();
			{
				setLoading(false);
				setCoupons(data);
				setCouponsFilter(data);
			}
		} catch (error) {
			console.log(error);
		}
	};

	const filter = (e) => {
		const keyword = e.target.value;

		if (keyword !== '') {
			const results = coupons.filter((coupon) => {
				return coupon.couponName.toLowerCase().startsWith(keyword.toLowerCase());
				// Use the toLowerCase() method to make it case-insensitive
			});
			setCouponsFilter(results);
		} else {
			setCouponsFilter(coupons);
			// If the text field is empty, show all users
		}

		setFilterKey(keyword);
	};
	const handleGetCouponDetails = (row) => {
		setUpdate(true);
		setCouponCode(row.couponCode);
		setCouponName(row.couponName);
		setCreatedDate(row.createdDate);
		setDiscountAmount(row.discountAmount);
		setActivated(row.activated);
		setRangeDate([dayjs(row.startDate), dayjs(row.expirationDate)]);

		setShowCard(true);
		setCoupon(row);
	};
	const displayStatus = (status) => {
		return status ? 'Đang hoạt động' : 'Không hoạt động';
	};

	const handleUpdateCoupon = async (e) => {
		// e.preventDefault();
		Object.assign(coupon, {couponCode: couponCode, discountAmount: discountAmount, expirationDate: moment(rangeDate[1].$d).format('yyyy-MM-DD'), startDate: moment(rangeDate[0].$d).format('yyyy-MM-DD'), activated: activated, couponName: couponName, createdDate: moment(createdDate).format('yyyy-MM-DD')});
		try {
			const resp = await fetch(ROOT_URL, {
				method: 'PUT',
				headers: {
					Accept: 'application/json',
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(coupon),
			});
			const data = resp.json();
			setCoupon(data);
		} catch (error) {
			console.log(error);
		}
		getData();
	};
	const isFormValid = (coupon) => {
		if (coupon.couponCode === '' || coupon.couponName === '' || coupon.discountAmount === 0) {
			if (coupon.couponCode === '') {
				setCouponCodeError('Vui lòng nhập vào Mã Khuyến Mãi !');
			} else {
				setCouponCodeError();
			}
			if (coupon.couponName === '') {
				setCouponNameError('Vui lòng nhập vào Tên Khuyến Mãi !');
			} else {
				setCouponNameError();
			}
			if (coupon.discountAmount === 0) {
				setDiscountAmountError('Vui lòng nhập vào Phần trăm Khuyến Mãi !');
			} else {
				setDiscountAmountError();
			}
			return false;
		} else {
			return true;
		}
	};
	const handleAddCoupon = async () => {
		Object.assign(coupon, {couponCode: couponCode, discountAmount: discountAmount, expirationDate: moment(rangeDate[1].$d).format('yyyy-MM-DD'), startDate: moment(rangeDate[0].$d).format('yyyy-MM-DD'), activated: activated, couponName: couponName, createdDate: moment(new Date()).format('yyyy-MM-DD')});
		console.log(JSON.stringify(coupon));
		// console.log();
		if (!isFormValid(coupon)) {
			return;
		}
		try {
			const resp = await fetch(ROOT_URL, {
				method: 'POST',
				headers: {
					Accept: 'application/json',
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(coupon),
			});
			const data = resp.json();
			getData();
			// setCoupons(...coupons, data);
		} catch (error) {
			console.log(error);
		}
		handleResetForm();
		setShowToast(true);
	};
	const handleShowCard = () => {
		setShowCard((pre) => !pre);

		setShowAddBtn((pre) => !pre);
	};
	const handleResetForm = () => {
		setUpdate(false);
		setCouponCode('');
		setCouponName('');
		setStartDate('');
		setDiscountAmount('');
		setExpirationDate('');
		setActivated(true);
		setCouponCodeError();
		setCouponNameError();
		setDiscountAmountError();
		setRangeDate([dayjs(new Date()), dayjs(new Date())]);
		setActivated((pre) => !pre);
	};

	const handleOnChangeSelect = (e) => {
		setActivated(e.target.value);
	};

	useEffect(() => {
		getData();
	}, []);

	return (
		<>
			<h2 className='text-center'>QUẢN LÝ KHUYẾN MÃI</h2>
			<Row className='mb-3'>
				<Row className='my-3 position-relative'>
					<Toast
						onClose={() => setShowToast(false)}
						show={showToast}
						autohide
						className='position-absolute top-0 start-50 translate-middle-x z-3 bg-success'>
						<Toast.Header>
							<strong className='me-auto'>4MEMS - Thông báo</strong>
						</Toast.Header>
						<Toast.Body className='text-white'>Cập nhật trạng thái đơn hàng thành công !</Toast.Body>
					</Toast>
				</Row>
				{showCard && (
					<>
						<Col xl={12}>
							<Card
								border='light'
								className='bg-white shadow-sm mb-4'>
								<Card.Body>
									<Row>
										<Col xl={6}>
											<h5 className='mb-4'>Thông tin khuyến mãi</h5>
										</Col>
										<Col xl={6}>
											<Button
												size='sm'
												onClick={() => {
													setShowCard((pre) => !pre);
													setShowAddBtn((pre) => !pre);
												}}
												className='float-end'
												variant='danger'>
												X
											</Button>
										</Col>
									</Row>

									<Form>
										<Row>
											<Col
												md={6}
												className='mb-3'>
												<Form.Group id='firstName'>
													<Form.Label>Mã khuyến mãi</Form.Label>
													<Form.Control
														onChange={(e) => setCouponCode(e.target.value)}
														required
														value={couponCode}
														maxLength={6}
														readOnly={update}
														type='text'
														placeholder='Mã khuyến mãi'
													/>
												</Form.Group>
												{couponCodeError && <i className='text-danger'>{couponCodeError}</i>}
											</Col>
											<Col
												md={6}
												className='mb-3'>
												<Form.Group id='lastName'>
													<Form.Label>Tên khuyến mãi</Form.Label>
													<Form.Control
														onChange={(e) => setCouponName(e.target.value)}
														required
														value={couponName}
														type='text'
														placeholder='Tên khuyến mãi'
													/>
												</Form.Group>
												{couponNameError && <i className='text-danger'>{couponNameError}</i>}
											</Col>
										</Row>
										<Row>
											<Col
												md={6}
												className='mb-3'>
												<Form.Group id='firstName'>
													<Form.Label>Phần trăm giảm giá</Form.Label>
													<Form.Control
														onChange={(e) => setDiscountAmount(e.target.value)}
														required
														value={discountAmount}
														type='number'
														min={0}
														max={100}
														step={10}
														placeholder='Phần trăm giảm giá'
													/>
												</Form.Group>
												{discountAmountError && <i className='text-danger'>{discountAmountError}</i>}
											</Col>
											<Col
												md={6}
												className='mb-3'>
												<Form.Group id='gender'>
													<Form.Label>Trạng thái</Form.Label>
													<Form.Select
														defaultValue={coupon.activated}
														value={activated}
														onChange={handleOnChangeSelect}>
														<option value='true'>Đang hoạt động</option>
														<option value='false'>Không hoạt động</option>
													</Form.Select>
												</Form.Group>
											</Col>
										</Row>
										<Row>
											<LocalizationProvider dateAdapter={AdapterDayjs}>
												<DemoItem
													label='Ngày bắt đầu - Ngày kết thúc'
													component='DateRangePicker'>
													<DateRangePicker
														value={rangeDate}
														minDate={!update ? dayjs(new Date()) : ''}
														// onChange={(newValue) => handleDateChange(newValue)}
														onChange={(newValue) => setRangeDate(newValue)}
													/>
												</DemoItem>
											</LocalizationProvider>
										</Row>

										<div className='mt-3'>
											<Button
												onClick={() => handleUpdateCoupon()}
												variant='info'
												disabled={!update}
												// type='submit'
											>
												Cập nhật
											</Button>

											<Button
												className='mx-2'
												onClick={() => handleAddCoupon()}
												variant='success'
												disabled={update}

												// type='submit'
											>
												Thêm khuyến mãi
											</Button>
											<Button
												className=''
												onClick={() => handleResetForm()}
												variant='gray'
												type='reset'>
												Làm mới
											</Button>
										</div>
									</Form>
								</Card.Body>
							</Card>
						</Col>
					</>
				)}
			</Row>
			<Row>
				<Col xl={5}>
					{showAddBtn && !showCard && (
						<Button
							className=''
							onClick={handleShowCard}
							variant='success'>
							Thêm khuyến mãi
						</Button>
					)}
				</Col>
				<Col
					xl={3}
					className='offset-3 mb-3'>
					<Form.Group id='firstName'>
						<Form.Control
							onChange={filter}
							required
							value={filterKey}
							maxLength={6}
							type='text'
							placeholder='Tìm kiếm'
						/>
					</Form.Group>
				</Col>
				<Col xl={1}>
					<Button
						variant='gray'
						onClick={() => {
							setFilterKey('');
							setCouponsFilter(coupons);
						}}>
						X
					</Button>
				</Col>
			</Row>
			<Row>
				<Col xl={12}>
					<DataTable
						columns={columns}
						data={couponsFilter}
						pagination
						striped
						highlightOnHover
						progressPending={loading}
						fixedHeader
						fixedHeaderScrollHeight='500px'
						conditionalRowStyles={conditionalRowStyles}
					/>
				</Col>
			</Row>
		</>
	);
};
