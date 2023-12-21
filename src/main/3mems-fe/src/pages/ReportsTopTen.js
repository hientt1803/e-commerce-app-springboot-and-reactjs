import React, {useEffect, useState} from 'react';
import {BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, Sector, PieChart, Pie} from 'recharts';

import DataTable from 'react-data-table-component';
import {Nav, Row, Tab, Col, Button} from '@themesberg/react-bootstrap';
import dayjs from 'dayjs';
import {DateRangePicker} from '@mui/x-date-pickers-pro/DateRangePicker';
import {AdapterDayjs} from '@mui/x-date-pickers/AdapterDayjs';
import {LocalizationProvider} from '@mui/x-date-pickers/LocalizationProvider';
import {DemoItem} from '@mui/x-date-pickers/internals/demo';
import moment from 'moment-timezone';

const ROOT_URL = 'http://localhost:8080/admin/report';

export default () => {
	const [reports, setReports] = useState([]);
	const [loading, setLoading] = useState(true);
	const [rangeDate, setRangeDate] = useState([dayjs(new Date()), dayjs(new Date())]);

	const [activeIndex, setActiveIndex] = useState(0);
	const data = reports.map((r) => {
		return {
			name: r.name,
			SL: r.quantity,
		};
	});

	const dataPie = reports.map((r) => {
		return {
			name: r.name,
			value: r.quantity,
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
			sortable: true,
			selector: (row) => row.category,
		},
		{
			name: 'Tên sản phẩm',
			sortable: true,
			selector: (row) => row.name,
		},
		{
			name: 'Tổng tiền',
			selector: (row) => row.price.toLocaleString('it-IT', {style: 'currency', currency: 'VND'}),
			sortable: true,
		},
		{
			name: 'Số lượng bán',
			selector: (row) => row.quantity,
			sortable: true,
		},
	];
	const conditionalRowStyles = [
		{
			when: (row) => row.price >= 500000,
			style: {backgroundColor: 'rgb(109, 235, 198)'},
		},
	];
	const getData = async () => {
		try {
			const resp = await fetch(ROOT_URL + '/report-top-ten');
			const data = await resp.json();
			setLoading(false);
			setReports(data);
		} catch (error) {
			console.log(error);
		}
	};
	const handleResetFilter = () => {
		getData();
		setRangeDate([dayjs(new Date()), dayjs(new Date())]);
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
					<div className='text-center'>Biểu đồ thể hiện top 10 sản phẩm bán chạy nhất</div>

					<Col xl={6}>
						<BarChart
							width={500}
							height={300}
							data={data}
							margin={{
								top: 5,
								right: 5,
								left: 5,
								bottom: 5,
							}}
							barSize={20}>
							<XAxis
								dataKey='name'
								scale='point'
								padding={{left: 10, right: 10}}
							/>
							<YAxis />
							<Tooltip />
							<Legend />
							<CartesianGrid strokeDasharray='3 3' />
							<Bar
								dataKey='SL'
								fill='#8884d8'
								background={{fill: '#eee'}}
							/>
						</BarChart>
					</Col>
					<Col xl={6}>
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
				<Col xl={2}>
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
