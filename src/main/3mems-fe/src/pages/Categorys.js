import {faCalendarAlt, faPenSquare} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {Button, Card, Col, Form, InputGroup, Row, Toast} from '@themesberg/react-bootstrap';
import {faSearch} from '@fortawesome/free-solid-svg-icons';
import React, {useEffect, useState} from 'react';
import DataTable from 'react-data-table-component';
const ROOT_URL = 'http://localhost:8080/admin/categorys';

export default () => {
	const [toastMessage, setToastMessage] = useState('');
	const [showCard, setShowCard] = useState(false);
	const [showAddBtn, setShowAddBtn] = useState(true);
	const [showToast, setShowToast] = useState(false);
	const [showUpdateBtn, setShowUpdateBtn] = useState(false);
	const [showNote, setShowNote] = useState(false);
	const handleCloseModal = () => setShowModal(false);
	const [loading, setLoading] = useState(true);
	const [newStatus, setNewStatus] = useState('');
	const [notes, setNotes] = useState('');
	const [showModal, setShowModal] = useState(false);
	const [categorys, setCategorys] = useState([{}]);
	const [update, setUpdate] = useState(false);

	//form
	const [id, setId] = useState('');
	const [name, setName] = useState('');
	const [errors, setErrors] = useState({});

	const [category, setCategory] = useState({
		id: '',
		name: '',
	});

	const columns = [
		{
			name: 'Mã danh mục',
			selector: (row) => row.id,
			sortable: true,
		},
		{
			name: 'Tên danh mục',
			selector: (row) => row.name,
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

	const getData = async () => {
		try {
			const resp = await fetch(ROOT_URL);
			const data = await resp.json();
			{
				setLoading(false);
				console.log(data);
				setCategorys(data);
				setRecords(data);
			}
		} catch (error) {
			console.log(error);
		}
	};

	const validateForm = () => {
		let formIsValid = true;
		const newErrors = {};

		if (!id) {
			newErrors.id = 'Vui lòng nhập mã danh mục';
			formIsValid = false;
		}

		if (!name) {
			newErrors.name = 'Vui lòng nhập tên danh mục';
			formIsValid = false;
		}

		setErrors(newErrors);
		return formIsValid;
	};

	const conditionalRowStyles = [
		{
			when: (row) => row.id === category.id,
			style: (row) => ({backgroundColor: 'rgb(109, 235, 198)'}),
		},
	];

	const [records, setRecords] = useState(categorys);

	function handleFilter(event) {
		const newData = categorys.filter((row) => {
			return row.name.toLowerCase().includes(event.target.value.toLowerCase());
		});
		if (newData.length === 0) {
			setToastMessage('Không tìm thấy danh mục!');
			setShowToast(true);
		}
		setRecords(newData);
	}

	const handleGetCouponDetails = (row) => {
		setUpdate(true);
		setId(row.id);
		setName(row.name);
		setShowCard(true);

		// setShowAddBtn(false);
		setCategory(row);
		console.log(category);
		// try {
		// 	const resp = await fetch(ROOT_URL + `/users/${row.id}`);
		// 	const data = await resp.json();
		// 	setShowCard(true);
		// 	setUser(data);
		// 	setCoupon(row);
		// } catch (error) {
		// 	console.log(error);
		// }
		// try {
		// 	const resp = await fetch(ROOT_URL + `/detail/${row.id}`);
		// 	const od = await resp.json();
		// } catch (error) {
		// 	console.log(error);
		// }
		// if (row.status === 'H' || row.status === 'G') {
		// 	setShowUpdateBtn(false);
		// } else {
		// 	setShowUpdateBtn(true);
		// }
	};

	const handleUpdateCoupon = async (e) => {
		// e.preventDefault();
		Object.assign(category, {id: id, name: name});

		console.log(category);
		console.log(JSON.stringify(category));
		try {
			const resp = await fetch(ROOT_URL, {
				method: 'PUT',
				headers: {
					Accept: 'application/json',
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(category),
			});
			const data = resp.json();
			setCategory(data);
		} catch (error) {
			console.log(error);
		}
		getData();
		console.log('updated');
	};

	const handleAddCoupon = async () => {
		Object.assign(category, {id: id, name: name});
		handleResetForm();
		try {
			const resp = await fetch(ROOT_URL, {
				method: 'POST',
				headers: {
					Accept: 'application/json',
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(category),
			});
			const data = resp.json();
			getData();
		} catch (error) {
			console.log(error);
		}
	};
	const handleResetForm = () => {
		setUpdate(false);
		setId('');
		setName('');
	};

	// const handleSubmitForm = async (e) => {
	// 	e.preventDefault();
	// 	handleCloseModal();
	// 	setShowNote(false);
	// 	setShowUpdateBtn(false);
	// 	Object.assign(category, {status: newStatus});
	// 	if (notes !== '') {
	// 		Object.assign(category, {notes: notes});
	// 	}
	// 	console.log(category);

	// 	try {
	// 		const resp = await fetch(ROOT_URL + `/${category.id}`, {
	// 			method: 'PUT',
	// 			headers: {
	// 				Accept: 'application/json',
	// 				'Content-Type': 'application/json',
	// 			},
	// 			body: JSON.stringify(category),
	// 		});
	// 		const data = resp.json();
	// 		setNotes('');
	// 	} catch (error) {
	// 		console.log(error);
	// 	}
	// 	setShowToast(true);
	// };

	const handleAdd = () => {
		if (validateForm()) {
			setToastMessage('Thêm danh mục thành công!');
			setShowToast(true);
		}
	};

	const handleUpdate = () => {
		if (validateForm()) {
			setToastMessage('Cập nhật danh mục thành công!');
			setShowToast(true);
		}
	};

	useEffect(() => {
		getData();
	}, []);

	return (
		<>
			<h2 className='text-center'>QUẢN LÝ DANH MỤC</h2>
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
						<Toast.Body className='text-white'>{toastMessage}</Toast.Body>
					</Toast>
				</Row>
				{showCard && (
					<>
						<Col xl={12}>
							<Card
								border='light'
								className='bg-white shadow-sm mb-4'>
								<Card.Body>
									<Form>
										<Row>
											<Col
												md={6}
												className='mb-3'>
												<Form.Group id='firstName'>
													<Form.Label>Mã danh mục</Form.Label>
													<Form.Control
														onChange={(e) => setId(e.target.value)}
														value={id}
														maxLength={4}
														readOnly={update}
														type='text'
														placeholder='Mã danh mục'
													/>
													{errors.id && <p className='alert alert-warning'>{errors.id}</p>}
												</Form.Group>
												{/* {errors.couponCode && <p className='alert alert-warning'>Vui lòng không để trống mã</p>} */}
											</Col>
											<Col
												md={6}
												className='mb-3'>
												<Form.Group id='lastName'>
													<Form.Label>Tên danh mục</Form.Label>
													<Form.Control
														onChange={(e) => setName(e.target.value)}
														value={name}
														type='text'
														placeholder='Tên danh mục'
													/>
													{errors.name && <p className='alert alert-warning'>{errors.name}</p>}
												</Form.Group>
											</Col>
										</Row>

										<div className='mt-3'>
											<Button
												className='mx-2'
												onClick={() => {
													handleAdd();
													handleAddCoupon();
												}}
												variant='success'
												disabled={update}

												// type='submit'
											>
												Thêm
											</Button>

											<Button
												className='mx-2'
												onClick={() => {
													handleUpdate();
													handleUpdateCoupon();
												}}
												variant='info'
												disabled={!update}
												// type='submit'
											>
												Cập nhật
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
				<Col
					xl={4}
					className='mb-3'>
					<InputGroup className='input-group-merge search-bar'>
						<InputGroup.Text>
							<FontAwesomeIcon icon={faSearch} />
						</InputGroup.Text>
						<Form.Control
							type='text'
							placeholder='Tìm theo tên danh mục'
							onChange={handleFilter}
						/>
					</InputGroup>
				</Col>
				{showAddBtn && (
					<Col xl={8}>
						<Button
							className='float-end'
							onClick={() => {
								setShowCard((pre) => !pre);
								setShowAddBtn(false);
							}}
							variant='success'>
							Thêm danh mục
						</Button>
					</Col>
				)}
				<Col xl={12}>
					<DataTable
						// expandableRows
						columns={columns}
						data={records}
						pagination
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
