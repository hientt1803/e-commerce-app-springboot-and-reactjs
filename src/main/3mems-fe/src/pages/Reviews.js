import {PieChart, Pie, Tooltip, BarChart, XAxis, YAxis, Legend, CartesianGrid, Bar} from 'recharts';
import {faTrashrAlt, faTrash} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {Button, Card, Col, Form, InputGroup, Row, Toast} from '@themesberg/react-bootstrap';
import {faSearch} from '@fortawesome/free-solid-svg-icons';
import React, {useEffect, useState} from 'react';
import DataTable from 'react-data-table-component';
import moment from 'moment-timezone';

const ROOT_URL = 'http://localhost:8080/admin/reviews';

export default () => {
	const [loading, setLoading] = useState(true);
	const [reviews, setReviews] = useState([]);
	const [products, setProducts] = useState([]);
	const [isLoading, setIsLoading] = useState(false);
	const [dataVersion, setDataVersion] = useState(0);
	const [data, setData] = useState([]);

	// const data = [
	//   { name: "Facebook", users: 2000000000 },
	//   { name: "Instagram", users: 1500000000 },
	//   { name: "Twiter", users: 1000000000 },
	//   { name: "Telegram", users: 500000000 },
	// ];

	const columns = [
		{
			name: 'Mã',
			selector: (row) => row.id,
			sortable: true,
			width: '100px',
		},
		{
			name: 'Tên sản phẩm',
			selector: (row) => row.product && row.product.name,
			sortable: true,
			width: '250px',
		},
		{
			name: 'Tên khách hàng',
			selector: (row) => row.account && row.account.fullname,
			sortable: true,
			width: '150px',
		},
		{
			name: 'Số sao',
			selector: (row) => row.rating,
			sortable: true,
			width: '100px',
		},
		{
			name: 'Nội dung',
			selector: (row) => row.textReview,
			sortable: true,
			width: '200px',
		},
		{
			name: 'Ngày đánh giá',
			selector: (row) => row.dateReview,
			sortable: true,
			width: '170px',
		},
		{
			button: true,
			name: 'Thao tác',
			cell: (row) => (
				<button
					className='btn btn-warning btn-xs'
					onClick={() => {
						handleDelete(row.id);
					}}>
					<FontAwesomeIcon
						icon={faTrash}
						size='lg'
						className='text-white'
					/>
				</button>
			),
		},
	];

	const handleDelete = async (id) => {
		try {
			const confirmed = window.confirm('Bạn có chắc chắn muốn xóa đánh giá này?');
			if (!confirmed) {
				return; // Người dùng đã hủy xóa
			}
			const response = await fetch(`http://localhost:8080/admin/reviews/${id}`, {
				method: 'DELETE',
			});
			if (response.ok) {
				setReviews(reviews.filter((review) => review.id !== id));
				alert('Xóa đánh giá thành công!');
			} else {
				throw new Error('Xóa đánh giá thất bại!');
			}
		} catch (error) {
			console.error('Failed to delete review:', error);
			alert('Failed to delete review.');
		}
	};

	const [searchText, setSearchText] = useState('');
	const [filteredData, setFilteredData] = useState([]);

	const handleSearch = (event) => {
		const value = event.target.value.toLowerCase();
		setSearchText(value);

		// Lọc dữ liệu dựa trên giá trị tìm kiếm
		const filteredRows = reviews.filter((row) => {
			if (row.account && row.account.fullname.toString().toLowerCase().includes(value)) {
				return true;
			}
			if (row.product && row.product.name.toString().toLowerCase().includes(value)) {
				return true;
			}
			const rowValues = Object.values(row);
			for (let i = 0; i < rowValues.length; i++) {
				const cellValue = rowValues[i];
				if (cellValue && cellValue.toString().toLowerCase().includes(value)) {
					return true;
				}
			}
			return false;
		});

		setFilteredData(filteredRows);
	};

	const fetchDataReport = async () => {
		try {
			const response = await fetch('http://localhost:8080/admin/reviews/report');
			const jsonData = await response.json();
			setData(jsonData);
		} catch (error) {
			console.log('Error fetching data from API:', error);
		}
	};
	const getData = async () => {
		try {
			const resp = await fetch(ROOT_URL);
			const data = await resp.json();
			setLoading(false);
			console.log(data);
			setReviews(data);
		} catch (error) {
			console.log(error);
		}
	};

	const fetchProductsData = async () => {
		try {
			const response = await fetch('http://localhost:8080/admin/products');
			const data = await response.json();
			setProducts(data);
		} catch (error) {
			console.log('Error: ' + error);
		}
	};

	useEffect(() => {
		const fetchData = async () => {
			try {
				setIsLoading(true);
				await Promise.all([getData(), fetchProductsData()]);
				setIsLoading(false);
				setDataVersion((prevVersion) => prevVersion + 1);
			} catch (error) {
				console.log('error: ' + error);
			}
		};
		fetchData();
		fetchDataReport();
	}, []);

	const CustomProductNameCell = ({row}) => {
		const productName = row.product && row.product.name;
		return <div>{productName}</div>;
	};

	return (
		<>
			<h2 className='text-center mb-4'>Thống kê trung bình số sao đánh giá của từng sản phẩm</h2>
			<BarChart
				width={1000}
				height={300}
				data={data}
				margin={{
					top: 5,
					right: 30,
					left: 80,
					bottom: 5,
				}}
				barSize={15}>
				<XAxis
					dataKey='ProductName'
					scale='point'
					padding={{left: 10, right: 10}}
				/>
				<YAxis />
				<Tooltip />
				<Legend />
				<CartesianGrid strokeDasharray='3 3' />
				<Bar
					dataKey='AverageRating'
					fill='#8884d8'
					background={{fill: '#eee'}}
				/>
			</BarChart>
			<Row className='mt-5'>
				<Col xl={4}>
					<InputGroup className='input-group-merge search-bar'>
						<InputGroup.Text>
							<FontAwesomeIcon icon={faSearch} />
						</InputGroup.Text>
						<Form.Control
							type='text'
							placeholder='Tìm kiếm'
							value={searchText}
							onChange={handleSearch}
						/>
					</InputGroup>
				</Col>
				<Col
					xl={12}
					className='mt-4'>
					<DataTable
						columns={columns}
						data={filteredData.length > 0 ? filteredData : reviews}
						pagination
						highlightOnHover
						progressPending={loading}
						fixedHeader
						fixedHeaderScrollHeight='500px'
						customStyles={{
							cells: {
								style: {
									whiteSpace: 'nowrap',
									overflow: 'hidden',
									textOverflow: 'ellipsis',
								},
							},
						}}
						customCell={CustomProductNameCell}
					/>
				</Col>
			</Row>
		</>
	);
};
