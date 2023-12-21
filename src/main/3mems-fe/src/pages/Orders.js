import {faList, faUser, faEye} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {Button, Card, Col, Form, Modal, Row, Table, Toast} from '@themesberg/react-bootstrap';
import React, {useEffect, useState, useRef} from 'react';
import DataTable from 'react-data-table-component';
const ROOT_URL = 'http://localhost:8080/admin/orders';

export default () => {
	const [showCard, setShowCard] = useState(false);
	const [showToast, setShowToast] = useState(false);
	const [showUpdateBtn, setShowUpdateBtn] = useState(false);
	const [orderDetails, setOrderDetails] = useState([]);
	const [showNote, setShowNote] = useState(false);
	const handleCloseModal = () => setShowModal(false);
	const [loading, setLoading] = useState(true);
	const [newStatus, setNewStatus] = useState('');
	const [user, setUser] = useState([]);
	const [notes, setNotes] = useState('');
	const [showModal, setShowModal] = useState(false);
	const [orders, setOrders] = useState([{}]);
	const columns = [
		{
			name: 'Mã đơn hàng',
			selector: (row) => row.id,
		},
		{
			name: 'Tổng tiền',
			sortable: true,
			selector: (row) => row.totalPrice,
		},
		{
			name: 'Ngày đặt',
			selector: (row) => row.createDate,
			sortable: true,
		},
		{
			name: 'Trạng thái',
			selector: (row) => displayStatus(row.status),
			sortable: true,
		},
		{
			button: true,
			name: 'Chi tiết',
			cell: (row) => (
				<button
					className='btn btn-info btn-xs'
					onClick={() => {
						console.log(row);
						handleGetOrderDetails(row);
					}}>
					<FontAwesomeIcon
						icon={faEye}
						size='lg'
						className='text-white'
					/>
				</button>
			),
		},
	];

	const [order, setOrder] = useState({
		address: '',
		createDate: '',
		id: -1,
		notes: '',
		phone: '',
		status: '',
		totalPrice: 0,
	});
	const getData = async () => {
		try {
			const resp = await fetch(ROOT_URL);
			const data = await resp.json();
			{
				setLoading(false);
				console.log(data);
				setOrders(data);
			}
		} catch (error) {
			console.log(error);
		}
	};
	const conditionalRowStyles = [
		{
			when: (row) => row.status === 'H',

			style: (row) => ({backgroundColor: row.activated ? 'inerit' : 'pink'}),
		},
		{
			when: (row) => row.id === order.id,

			style: (row) => ({border: '1px grey solid'}, {backgroundColor: 'rgb(109, 235, 198)'}),
		},
	];
	const handleGetOrderDetails = async (row) => {
		setShowNote(false);
		setShowCard(true);
		if (row.id === order.id) {
			return;
		}
		setOrder(row);
		try {
			const resp = await fetch(ROOT_URL + `/users/${row.id}`);
			const data = await resp.json();
			console.log(data);
			setUser(data);
			setShowCard(true);
		} catch (error) {
			console.log(error);
		}
		try {
			const resp = await fetch(ROOT_URL + `/detail/${row.id}`);
			const od = await resp.json();
			setOrderDetails(od);
		} catch (error) {
			console.log(error);
		}
		if (row.status === 'H' || row.status === 'DG') {
			setShowUpdateBtn(false);
		} else {
			setShowUpdateBtn(true);
		}
	};
	const displayStatus = (status) => {
		switch (status) {
			case 'H':
				return 'Đã hủy';
				break;
			case 'XL':
				return 'Đang chờ xử lý';
				break;
			case 'G':
				return 'Đang giao';
				break;
			case 'DG':
				return 'Đã giao';
				break;
			default:
				return 'Không có ';
				break;
		}
	};

	const handleShowModal = () => setShowModal(true);

	const renderSelect = (status) => {
		switch (status) {
			case 'XL':
				return (
					<>
						<option value='Xl'>Chờ xử lý</option>
						<option value='G'>Đang giao</option>
						<option value='H'>Hủy</option>
					</>
				);
				break;
			case 'DG':
				return (
					<>
						<option value='G'>Đang giao</option>
						<option value='DG'>Đã giao</option>
						<option value='H'>Hủy</option>
					</>
				);
				break;

			default:
				break;
		}
	};
	const displayBadgeStatus = (status) => {
		switch (status) {
			case 'H':
				return 'danger';
				break;
			case 'XL':
				return 'secondary';
				break;
			case 'DG':
				return 'success';
				break;
			case 'G':
				return 'success';
				break;
			default:
				break;
		}
	};

	const handleOnChangeSelect = (e) => {
		if (e.target.value === order.status) {
			setShowUpdateBtn(false);
			setShowNote(false);
		} else if (e.target.value === 'H') {
			setShowNote(true);
			setShowUpdateBtn(true);
		} else {
			setShowNote(false);
			setShowUpdateBtn(true);
		}
		setNewStatus(e.target.value);
	};

	const handleSubmitForm = async (e) => {
		e.preventDefault();
		handleCloseModal();
		setShowNote(false);
		setShowUpdateBtn(false);
		Object.assign(order, {status: newStatus});
		if (notes !== '') {
			Object.assign(order, {notes: notes});
		}
		console.log(order);

		try {
			const resp = await fetch(ROOT_URL + `/${order.id}`, {
				method: 'PUT',
				headers: {
					Accept: 'application/json',
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(order),
			});
			const data = resp.json();
			setNotes('');
		} catch (error) {
			console.log(error);
		}
		setShowToast(true);
	};

	const handleChangeTextarea = (e) => {
		setNotes(e.target.value);
	};

	useEffect(() => {
		getData();
	}, [order]);

	return (
		<>
			<h2 className='text-center'>QUẢN LÝ ĐƠN ĐẶT HÀNG</h2>
			{/* cmt to push */}
			<Row className='mb-3'>
				{showCard && (
					<>
						<Col xl={3}>
							<Card className={`position-relative  border-2 border-${displayBadgeStatus(order.status)}`}>
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
								<Card.Body>
									<Card.Title className='text-center fw-bold'>
										{' '}
										<FontAwesomeIcon
											icon={faUser}
											className={`text-${displayBadgeStatus(order.status)}`}
										/>
										<span className='mx-2'>Thông tin khách hàng</span>
									</Card.Title>
									<div className='mb-2'>
										Họ và tên: <strong>{user.fullname}</strong>
									</div>
									<div className='mb-2'>
										Địa chi: <strong>{order.address}</strong>
									</div>
									<div className='mb-2'>
										Số điện thoại: <strong>{order.phone}</strong>
									</div>
									<div className='mb-2'>
										Tổng tiền thanh toán: <strong>{order.totalPrice.toLocaleString('it-IT', {style: 'currency', currency: 'VND'})}</strong>
									</div>
									<div className='mb-2'>
										Trạng thái đơn hàng: <strong className={`badge  bg-${displayBadgeStatus(order.status)}`}> {displayStatus(order.status)}</strong>
										{(order.status === 'XL' || order.status === 'G') && (
											<Form.Select
												onChange={handleOnChangeSelect}
												aria-label='Default select example'
												size='sm'
												defaultValue={order.status}>
												{renderSelect(order.status)}
											</Form.Select>
										)}
										{showNote && (
											<>
												<Form.Control
													onChange={handleChangeTextarea}
													as='textarea'
													value={notes}
													className='mt-2'
													placeholder='Lý do hủy đơn hàng'
													style={{height: '50px'}}
												/>
											</>
										)}
									</div>
									{order.status === 'H' && (
										<div className='mb-2'>
											Lý đo hủy: <u>{order.notes}</u>
										</div>
									)}
									{showUpdateBtn && (order.status !== 'H' || order.status !== 'DG') && (
										<>
											<Row>
												<Col xl={6}>
													<Button
														disabled={newStatus === '' || (showNote && notes === '')}
														onClick={handleShowModal}
														variant='primary'
														size='sm'>
														Cập nhật trạng thái
													</Button>
												</Col>
											</Row>
											<Modal
												show={showModal}
												onHide={handleCloseModal}
												backdrop='static'
												keyboard={false}>
												<Modal.Header closeButton>
													<Modal.Title>Xác nhận câp nhật trạng thái đơn hàng</Modal.Title>
												</Modal.Header>
												<Modal.Body>
													Bạn có chắc chắn cập nhật lại trạng thái của đơn hàng có mã là <strong className='fw-bold badge bg-info'> {order.id} </strong>từ <strong className='fw-bold badge bg-info'>{displayStatus(order.status)}</strong> thành <strong className='fw-bold badge bg-info'> {displayStatus(newStatus)}</strong> không ??
												</Modal.Body>
												<Modal.Footer>
													<Button
														variant='secondary'
														size='sm'
														onClick={handleCloseModal}>
														Không, hủy
													</Button>
													<Button
														onClick={handleSubmitForm}
														variant='primary'
														size='sm'>
														Có, cập nhật
													</Button>
												</Modal.Footer>
											</Modal>
										</>
									)}
								</Card.Body>
							</Card>
						</Col>
						<Col
							xl={9}
							className='position-relative'>
							<Card>
								<Card.Body>
									<Card.Title className='text-center fw-bold'>
										<FontAwesomeIcon icon={faList} />
										<span className='mx-2'>Thông tin chi tiết đơn hàng</span>
									</Card.Title>
									{order.id !== -1 && (
										<Card.Title className='text-center fw-bold alert alert-info'>
											<span className='mx-2'>Mã đơn hàng {order.id}</span>
										</Card.Title>
									)}
									<Table
										striped
										bordered
										hover>
										<thead>
											<tr>
												<th>Mã Sản phẩm</th>
												<th>Giá</th>
												<th>Tên sản phẩm</th>
												<th>Số lượng</th>
											</tr>
										</thead>
										<tbody>
											{orderDetails.map((od) => {
												const {id, name, image} = od.product;
												return (
													<tr key={id}>
														<td>{id}</td>
														<td>{od.price.toLocaleString('it-IT', {style: 'currency', currency: 'VND'})}</td>
														<td>{name}</td>
														<td>{od.quantity}</td>
													</tr>
												);
											})}
										</tbody>
									</Table>
								</Card.Body>
							</Card>
						</Col>
					</>
				)}
			</Row>
			<Row>
				<Col xl={12}>
					<DataTable
						// expandableRows
						columns={columns}
						data={orders}
						striped
						pagination
						highlightOnHover
						progressPending={loading}
						onRowDoubleClicked={handleGetOrderDetails}
						fixedHeader
						conditionalRowStyles={conditionalRowStyles}
						fixedHeaderScrollHeight='500px'
					/>
				</Col>
			</Row>
		</>
	);
};
