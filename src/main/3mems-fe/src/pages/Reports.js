import React, {useEffect, useState} from 'react';
import DataTable from 'react-data-table-component';
import {Nav, Tab} from '@themesberg/react-bootstrap';
import ReportsTopTen from './ReportsTopTen';
import ReportsByCategory from './ReportsByCategory';
import ReportsByProduct from './ReportsByProduct';
import ReportsByUser from './ReportsByUser';
const ROOT_URL = 'http://localhost:8080/admin/report';
export default () => {
	return (
		<>
			<h2 className='text-center'>TỔNG HỢP - THỐNG KÊ</h2>
			<Tab.Container defaultActiveKey='top-ten'>
				<Nav
					fill
					variant='pills'
					className='flex-column flex-sm-row'>
					<Nav.Item>
						<Nav.Link
							eventKey='top-ten'
							className='mb-sm-3 mb-md-0'>
							Top 10 sản phẩm bán chạy
						</Nav.Link>
					</Nav.Item>
					<Nav.Item>
						<Nav.Link
							eventKey='category'
							className='mb-sm-3 mb-md-0'>
							Danh mục
						</Nav.Link>
					</Nav.Item>
					<Nav.Item>
						<Nav.Link
							eventKey='product'
							className='mb-sm-3 mb-md-0'>
							Sản phẩm
						</Nav.Link>
					</Nav.Item>
					<Nav.Item>
						<Nav.Link
							eventKey='user'
							className='mb-sm-3 mb-md-0'>
							Người dùng
						</Nav.Link>
					</Nav.Item>
				</Nav>
				<Tab.Content>
					<Tab.Pane
						eventKey='top-ten'
						className='py-4'>
						<ReportsTopTen />
					</Tab.Pane>
					<Tab.Pane
						eventKey='category'
						className='py-4'>
						<ReportsByCategory />
					</Tab.Pane>
					<Tab.Pane
						eventKey='product'
						className='py-4'>
						<ReportsByProduct />
					</Tab.Pane>
					<Tab.Pane
						eventKey='user'
						className='py-4'>
						<ReportsByUser />
					</Tab.Pane>
				</Tab.Content>
			</Tab.Container>
		</>
	);
};
