import React, {useEffect, useState} from 'react';
import {Redirect, Route, Switch} from 'react-router-dom';
import {Routes} from '../routes';

// pages

import ForgotPassword from './examples/ForgotPassword';
import Lock from './examples/Lock';
import NotFoundPage from './examples/NotFound';
import ResetPassword from './examples/ResetPassword';
import ServerError from './examples/ServerError';
import Signin from './examples/Signin';
import Signup from './examples/Signup';
import BootstrapTables from './tables/BootstrapTables';

// documentation pages
import DocsBuild from './documentation/DocsBuild';
import DocsChangelog from './documentation/DocsChangelog';
import DocsDownload from './documentation/DocsDownload';
import DocsFolderStructure from './documentation/DocsFolderStructure';
import DocsLicense from './documentation/DocsLicense';
import DocsOverview from './documentation/DocsOverview';
import DocsQuickStart from './documentation/DocsQuickStart';

// components
import Navbar from '../components/Navbar';
import Preloader from '../components/Preloader';
import Sidebar from '../components/Sidebar';

import Coupons from './Coupons';
import Dashboard from './Dashboard';
import Orders from './Orders';
import Product from './Product';
import Reports from './Reports';
import Reviews from './Reviews';
import Categorys from './Categorys';
import Accordion from './components/Accordion';
import Alerts from './components/Alerts';
import Badges from './components/Badges';
import Breadcrumbs from './components/Breadcrumbs';
import Buttons from './components/Buttons';
import Forms from './components/Forms';
import Modals from './components/Modals';
import Navbars from './components/Navbars';
import Navs from './components/Navs';
import Pagination from './components/Pagination';
import Popovers from './components/Popovers';
import Progress from './components/Progress';
import Tables from './components/Tables';
import Tabs from './components/Tabs';
import Toasts from './components/Toasts';
import Tooltips from './components/Tooltips';
import Account from './Account';

const RouteWithLoader = ({component: Component, ...rest}) => {
	const [loaded, setLoaded] = useState(false);

	useEffect(() => {
		const timer = setTimeout(() => setLoaded(true), 1000);
		return () => clearTimeout(timer);
	}, []);

	return (
		<Route
			{...rest}
			render={(props) => (
				<>
					{' '}
					<Preloader show={loaded ? false : true} /> <Component {...props} />{' '}
				</>
			)}
		/>
	);
};

const RouteWithSidebar = ({component: Component, ...rest}) => {
	const [loaded, setLoaded] = useState(false);

	useEffect(() => {
		const timer = setTimeout(() => setLoaded(true), 1000);
		return () => clearTimeout(timer);
	}, []);

	return (
		<Route
			{...rest}
			render={(props) => (
				<>
					<Preloader show={loaded ? false : true} />
					<Sidebar />
					<main className='content'>
						<Navbar />
						<Component {...props} />
						{/* <Footer
							toggleSettings={toggleSettings}
							showSettings={showSettings}
						/> */}
					</main>
				</>
			)}
		/>
	);
};

export default () => (
	<Switch>
		<RouteWithLoader
			exact
			path={Routes.Signin.path}
			component={Signin}
		/>
		<RouteWithLoader
			exact
			path={Routes.Signup.path}
			component={Signup}
		/>
		<RouteWithLoader
			exact
			path={Routes.ForgotPassword.path}
			component={ForgotPassword}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Account.path}
			component={Account}
		/>
		<RouteWithLoader
			exact
			path={Routes.ResetPassword.path}
			component={ResetPassword}
		/>
		<RouteWithLoader
			exact
			path={Routes.Lock.path}
			component={Lock}
		/>
		<RouteWithLoader
			exact
			path={Routes.NotFound.path}
			component={NotFoundPage}
		/>
		<RouteWithLoader
			exact
			path={Routes.ServerError.path}
			component={ServerError}
		/>
		{/* pages */}
		{/* <RouteWithSidebar
			exact
			path={Routes.DashboardOverview.path}
			component={DashboardOverview}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Upgrade.path}
			component={Upgrade}
		/> */}
		<RouteWithSidebar
			exact
			path={Routes.Dashboard.path}
			component={Dashboard}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Orders.path}
			component={Orders}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Product.path}
			component={Product}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Reports.path}
			component={Reports}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Reviews.path}
			component={Reviews}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Coupons.path}
			component={Coupons}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Categorys.path}
			component={Categorys}
		/>
		<RouteWithSidebar
			exact
			path={Routes.BootstrapTables.path}
			component={BootstrapTables}
		/>
		{/* components */}
		<RouteWithSidebar
			exact
			path={Routes.Accordions.path}
			component={Accordion}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Alerts.path}
			component={Alerts}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Badges.path}
			component={Badges}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Breadcrumbs.path}
			component={Breadcrumbs}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Buttons.path}
			component={Buttons}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Forms.path}
			component={Forms}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Modals.path}
			component={Modals}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Navs.path}
			component={Navs}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Navbars.path}
			component={Navbars}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Pagination.path}
			component={Pagination}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Popovers.path}
			component={Popovers}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Progress.path}
			component={Progress}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Tables.path}
			component={Tables}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Tabs.path}
			component={Tabs}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Tooltips.path}
			component={Tooltips}
		/>
		<RouteWithSidebar
			exact
			path={Routes.Toasts.path}
			component={Toasts}
		/>
		{/* documentation */}
		<RouteWithSidebar
			exact
			path={Routes.DocsOverview.path}
			component={DocsOverview}
		/>
		<RouteWithSidebar
			exact
			path={Routes.DocsDownload.path}
			component={DocsDownload}
		/>
		<RouteWithSidebar
			exact
			path={Routes.DocsQuickStart.path}
			component={DocsQuickStart}
		/>
		<RouteWithSidebar
			exact
			path={Routes.DocsLicense.path}
			component={DocsLicense}
		/>
		<RouteWithSidebar
			exact
			path={Routes.DocsFolderStructure.path}
			component={DocsFolderStructure}
		/>
		<RouteWithSidebar
			exact
			path={Routes.DocsBuild.path}
			component={DocsBuild}
		/>
		<RouteWithSidebar
			exact
			path={Routes.DocsChangelog.path}
			component={DocsChangelog}
		/>
		<Redirect to={Routes.NotFound.path} />
	</Switch>
);
