import React, {useEffect, useState} from 'react';
import DataTable from 'react-data-table-component';
import {Button, Col, Nav, Row, Tab} from '@themesberg/react-bootstrap';
import {LocalizationProvider} from '@mui/x-date-pickers';
import dayjs from 'dayjs';
import {AdapterDayjs} from '@mui/x-date-pickers/AdapterDayjs';
import {DemoItem} from '@mui/x-date-pickers/internals/demo';
import {DateRangePicker} from '@mui/x-date-pickers-pro/DateRangePicker';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import moment from 'moment-timezone';
import {BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, Sector, PieChart, Pie} from 'recharts';

const ROOT_URL = 'http://localhost:8080/admin/report';
export default () => {
	const [reports, setReports] = useState([]);
	const [loading, setLoading] = useState(true);
	const [activeIndex, setActiveIndex] = useState(0);

	const [rangeDate, setRangeDate] = useState([dayjs(new Date()), dayjs(new Date())]);
	const data = reports.map((r) => {
		return {
			name: r.category,
			SL: r.quantity,
		};
	});

	const dataPie = reports.map((r) => {
		return {
			name: r.category,
			value: r.totalPrice,
		};
	});
	const renderActiveShape = (props) => {
		const RADIAN = Math.PI / 180;
		const {cx, cy, midAngle, innerRadius, outerRadius, startAngle, endAngle, fill, payload, percent, value} = props;
		const sin = Math.sin(-RADIAN * midAngle);
		const cos = Math.cos(-RADIAN * midAngle);
		const sx = cx + (outerRadius + 10) * cos;
		const sy = cy + (outerRadius + 10) * sin;
		const mx = cx + (outerRadius + 30) * cos;
		const my = cy + (outerRadius + 30) * sin;
		const ex = mx + (cos >= 0 ? 1 : -1) * 22;
		const ey = my;
		const textAnchor = cos >= 0 ? 'start' : 'end';

		return (
			<g>
				<text
					x={cx}
					y={cy}
					dy={8}
					textAnchor='middle'
					fill={fill}>
					{payload.name}
				</text>
				<Sector
					cx={cx}
					cy={cy}
					innerRadius={innerRadius}
					outerRadius={outerRadius}
					startAngle={startAngle}
					endAngle={endAngle}
					fill={fill}
				/>
				<Sector
					cx={cx}
					cy={cy}
					startAngle={startAngle}
					endAngle={endAngle}
					innerRadius={outerRadius + 6}
					outerRadius={outerRadius + 10}
					fill={fill}
				/>
				<path
					d={`M${sx},${sy}L${mx},${my}L${ex},${ey}`}
					stroke={fill}
					fill='none'
				/>
				<circle
					cx={ex}
					cy={ey}
					r={2}
					fill={fill}
					stroke='none'
				/>
				<text
					x={ex + (cos >= 0 ? 1 : -1) * 12}
					y={ey}
					textAnchor={textAnchor}
					fill='#333'>{`Bán được ${value}`}</text>
				<text
					x={ex + (cos >= 0 ? 1 : -1) * 12}
					y={ey}
					dy={18}
					textAnchor={textAnchor}
					fill='#999'>
					{`(Tỉ lệ: ${(percent * 100).toFixed(2)}%)`}
				</text>
			</g>
		);
	};

	const onPieEnter = (_, index) => {
		setActiveIndex(index);
	};
	const columns = [
		{
			name: 'Danh mục sản phẩm',
			selector: (row) => row.category,
			sortable: true,
		},
		{
			name: 'Số lượng bán',
			selector: (row) => row.quantity,
			sortable: true,
		},
		{
			name: 'Tổng tiền',
			selector: (row) => row.totalPrice.toLocaleString('it-IT', {style: 'currency', currency: 'VND'}),
			sortable: true,
		},
	];
	const conditionalRowStyles = [
		{
			when: (row) => row.totalPrice >= 500000,
			style: {backgroundColor: 'rgb(109, 235, 198)'},
		},
	];
	const handleResetFilter = () => {
		getData();
		setRangeDate([dayjs(new Date()), dayjs(new Date())]);
	};
	const getData = async () => {
		try {
			const resp = await fetch(ROOT_URL + '/report-by-category');
			const data = await resp.json();
			{
				setLoading(false);
				console.log(data);
				setReports(data);
			}
		} catch (error) {
			console.log(error);
		}
	};

	const handleFilter = async () => {
		console.log(moment(rangeDate[0].$d).format('yyyy-MM-DD'));
		console.log(moment(rangeDate[1].$d).format('yyyy-MM-DD'));
		try {
			const resp = await fetch(ROOT_URL + `/report-by-category/filter?startDate= ${moment(rangeDate[0].$d).format('yyyy-MM-DD')}&endDate=${moment(rangeDate[1].$d).format('yyyy-MM-DD')}`);
			const data = await resp.json();
			setLoading(false);
			console.log(data);
			setReports(data);
		} catch (error) {
			console.log(error);
		}
	};
	useEffect(() => {
		getData();
	}, []);
	return (
		<>
			{reports.length != 0 && (
				<Row>
					<Col xl={6}>
						<div className='text-center'>Biểu đồ thể hiện tổng số lượng sản phẩm bán được theo danh mục</div>

						<BarChart
							width={500}
							height={300}
							data={data}
							margin={{
								top: 5,
								right: 30,
								left: 20,
								bottom: 5,
							}}>
							<CartesianGrid strokeDasharray='3 3' />
							<XAxis dataKey='name' />
							<YAxis />
							<Tooltip />
							<Legend />
							<Bar
								dataKey='SL'
								fill='#8884d8'
							/>
						</BarChart>
					</Col>
					<Col xl={6}>
						<div className='text-center'>Biểu đồ thể hiện tổng thành tiền bán của sản phẩm theo danh mục</div>

						<PieChart
							width={500}
							height={500}>
							<Pie
								activeIndex={activeIndex}
								activeShape={renderActiveShape}
								data={dataPie}
								cx='50%'
								cy='50%'
								innerRadius={60}
								outerRadius={80}
								fill='#8884d8'
								dataKey='value'
								onMouseEnter={onPieEnter}
							/>
						</PieChart>
					</Col>
				</Row>
			)}
			<Row>
				<Col xl={4}>
					<LocalizationProvider dateAdapter={AdapterDayjs}>
						<DemoItem
							label='Ngày bắt đầu - Ngày kết thúc'
							component='DateRangePicker'>
							<DateRangePicker
								value={rangeDate}
								onChange={(newValue) => setRangeDate(newValue)}
							/>
						</DemoItem>
					</LocalizationProvider>
				</Col>
				<Col xl={4}>
					<div className='pb-3 mb-2'>
						<Button
							onClick={() => handleFilter()}
							variant='info'
							size='sm'
							className='mt-5'
							// disabled={!update}
						>
							Xem thống kê
						</Button>
						<Button
							onClick={handleResetFilter}
							variant='info'
							size='sm'
							className='mt-5 ms-3'
							// disabled={!update}
						>
							X
						</Button>
					</div>
				</Col>
			</Row>
			<DataTable
				// expandableRows
				columns={columns}
				data={reports}
				striped
				pagination
				highlightOnHover
				progressPending={loading}
				fixedHeader
				conditionalRowStyles={conditionalRowStyles}
				fixedHeaderScrollHeight='500px'
			/>
		</>
	);
};
