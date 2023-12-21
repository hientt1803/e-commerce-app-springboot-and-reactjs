import React, { useCallback, useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCheck,
  faCog,
  faHome,
  faSearch,
} from "@fortawesome/free-solid-svg-icons";
import {
  Col,
  Row,
  Form,
  Button,
  ButtonGroup,
  Breadcrumb,
  InputGroup,
  Dropdown,
} from "@themesberg/react-bootstrap";

import { TransactionsTable } from "../components/Tables";

const url = "http://localhost:8080/admin/index";
const imageUrl = "http://localhost:8080/img/product/";

export default () => {
  const [topTenOrderDetails, setTopTenOrderDetails] = useState([]);
  const [bestSellingProduct, setBestSellingProduct] = useState([]);
  const [statusProduct, setStatusProduct] = useState([]);
  const [productNewest, setProductNewest] = useState([]);
  const [totalReneue, setTotalReneue] = useState([]);
  const [totalProductSoldToday, setTotalProductSoldToday] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  // fetchBestingSelling
  const fetchBestSellingProduct = async () => {
    try {
      const response = await fetch(`${url}/bestSellingProduct`);
      const data = await response.json();
      const bestSelling = data;

      if (bestSelling) {
        const newbestSelling = bestSelling.map((item) => {
          const { id, name } = item;
          return {
            id: id,
            name: name,
          };
        });
        setBestSellingProduct(newbestSelling);
      } else {
        setBestSellingProduct([]);
      }
    } catch (error) {
      console.log("Error: " + error);
    }
  };

  // ProductNewset
  const fetchProductNewest = async () => {
    try {
      const response = await fetch(`${url}/selectProductNewest`);
      const data = await response.json();
      const newproductNewest = data;
      if (newproductNewest.length === 0) {
        setProductNewest(0);
      } else {
        setProductNewest(newproductNewest);
      }
    } catch (error) {
      console.log("Error: " + error);
    }
  };

  // TotalRenue
  const fetchtotalRenue = async () => {
    try {
      const response = await fetch(`${url}/totalRevenue`);
      const data = await response.json();
      const newTotalRenue = data;
      if (newTotalRenue === null) {
        setTotalReneue(0);
        console.log(totalReneue);
      } else {
        setTotalReneue(newTotalRenue);
      }
    } catch (error) {
      console.log("Error: " + error);
    }
  };

  // TotalProductSoldToday
  const fetchTotalProductsSoldToday = async () => {
    try {
      const response = await fetch(`${url}/totalProductsSoldToday`);
      const data = await response.json();
      const newTotalProductSoldToday = data;
      setTotalProductSoldToday(newTotalProductSoldToday);
    } catch (error) {
      console.log("Error: " + error);
    }
  };

  // StatusProduct
  const fetchStatusProduct = async () => {
    try {
      const response = await fetch(`${url}/findByStatusC`);
      const data = await response.json();
      const newStatusProduct = data;
      if (newStatusProduct.length === 0) {
        setStatusProduct(0);
      }
      setStatusProduct(newStatusProduct.length);
    } catch (error) {
      console.log("Error: " + error);
    }
  };

  // StatusProduct
  const fetchPageTopTenOrderDetails = async () => {
    try {
      const response = await fetch(`${url}/getPageTopTenOrderDetails`);
      const data = await response.json();
      const newTopTenOrder = data;
      setTopTenOrderDetails(newTopTenOrder);
    } catch (error) {
      console.log("Error: " + error);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoading(true); // Start fetching, isLoading is true

        await Promise.all([
          fetchBestSellingProduct(),
          fetchProductNewest(),
          fetchtotalRenue(),
          fetchTotalProductsSoldToday(),
          fetchStatusProduct(),
          fetchPageTopTenOrderDetails(),
        ]);

        setIsLoading(false); // Completed fetching, isLoading is false
      } catch (error) {
        console.log("error: " + error);
      }
    };

    fetchData();
  }, []);

  return (
    <>
      {isLoading ? (
        <h3 className="text-center bg-white">Loading...</h3>
      ) : (
        <>
          <div
            className="d-flex justify-content-between 
              container-fluid flex-wrap 
              flex-md-nowrap align-items-center py-4"
          >
            <div className="d-block mb-4 mb-md-0">
              <Breadcrumb
                className="d-none d-md-inline-block"
                listProps={{
                  className: "breadcrumb-dark breadcrumb-transparent",
                }}
              >
                <Breadcrumb.Item>
                  <FontAwesomeIcon icon={faHome} />
                </Breadcrumb.Item>
                <Breadcrumb.Item active>Trang Chủ</Breadcrumb.Item>
              </Breadcrumb>
              <h3>
                Chào mừng trở lại!
              </h3>
              {/* <p className="mb-0">Your web analytics dashboard template.</p> */}
            </div>

            {/* <div className="btn-toolbar mb-2 mb-md-0">
            <ButtonGroup>
              <Button variant="outline-primary" size="sm">Share</Button>
              <Button variant="outline-primary" size="sm">Export</Button>
            </ButtonGroup>
          </div> */}
          </div>

          {/* statistic concept*/}
          <div className="container-fluid d-flex justify-content-start gap-3">
            {/* Item */}
            <div
              className="card text-center col-xl-3 col-lg-3 col-md-6 col-sm-12"
              style={{ background: "#effcef" }}
            >
              <div className="card-body">
                <h3 className="card-title fw-bold fs-3">
                  {productNewest.length}
                </h3>
                <p className="text-dark fw-semibold">Sản phẩm vừa thêm</p>
              </div>
            </div>
            {/* Item */}
            <div
              className="card text-center col-xl-3 col-lg-3 col-md-6 col-sm-12"
              style={{ background: "#e6f5f9" }}
            >
              <div className="card-body">
                <h3 className="card-title fw-bold fs-3">
                  {totalReneue.toLocaleString("it-IT", {
                    style: "currency",
                    currency: "VND",
                  })}
                </h3>
                <p className="text-dark fw-semibold">Tổng doanh thu</p>
              </div>
            </div>
            {/* Item */}
            <div
              className="card text-center col-xl-3 col-lg-3 col-md-6 col-sm-12"
              style={{ background: "#f4f6fa" }}
            >
              <div className="card-body">
                <h3 className="card-title fw-bold fs-3">
                  {totalProductSoldToday}
                </h3>
                <p className="text-dark fw-semibold">
                  Tổng sản phẩm bán được trong ngày
                </p>
              </div>
            </div>
            {/* Item */}
            <div
              className="card text-center col-xl-3 col-lg-3 col-md-6 col-sm-12"
              style={{ background: "#f4f6fa" }}
            >
              <div className="card-body">
                <h3 className="card-title fw-bold fs-3">{statusProduct}</h3>
                <p className="text-dark fw-semibold">
                  Tổng sản phẩm chờ xác nhận
                </p>
              </div>
            </div>
          </div>

          {/* Content Transaction */}
          <div className="main-content container-fluid mt-4">
            <div className="row">
              <h4>
                Tổng Giao dịch trong ngày:{" "}
                <strong>{topTenOrderDetails.length}</strong>
              </h4>
              {/* Item */}
              <div className="col-8">
                <div className="card-full-height">
                  <div className="card h-100">
                    <div className="card-body">
                      <h4 className="card-title fw-bold mb-4">
                        <i class="fas fa-play"></i> Giao dịch
                      </h4>
                      <div className="row">
                        {/* {topTenOrderDetails.map((orderDetail) => (
                        <div className="col-12 mb-4" key={orderDetail.id}>
                          <div className="d-flex justify-content-between align-items-center flex-wrap">
                            <div className="p-2 d-flex justify-content-center align-items-center" style={{ background: '#dc3545', width: '35px', height: '35px', borderRadius: '50%' }}>
                              <i class="fa fa-headphones" aria-hidden="true" style={{ color: "#fff" }}></i>
                            </div>
                            <div className="font-weight-bold d-flex flex-column">
                              <h6 className="text-truncate" style={{ maxWidth: '250px' }}>{orderDetail.product.name}</h6>
                              <p className="text-muted">{orderDetail.product.createDate}</p>
                            </div>
                            <div className="font-weight-bold d-flex flex-column">
                              <h6 style={{ color: '#dc3545' }}>
                                {orderDetail.price} | {orderDetail.quantity}
                              </h6>
                              <p className="text-muted">{orderDetail.id}</p>
                            </div>
                          </div>
                          <hr className="m-0 p-0" />
                        </div>
                      ))} */}
                        <div class="table-responsive">
                          <table
                            class="table table-striped
                        table-hover
                        align-middle"
                          >
                            <thead class="">
                              <caption>Transaction</caption>
                              <tr style={{ background: "#e6f5f9" }}>
                                <th>ID</th>
                                <th>Hình ảnh</th>
                                <th>Tên sản phẩm</th>
                                <th>Giá sản phẩm</th>
                                <th>Tổng tiền</th>
                                <th>Số lượng</th>
                              </tr>
                            </thead>
                            <tbody class="table-group-divider">
                              {topTenOrderDetails.map((o) => (
                                <tr key={o.id}>
                                  <td>{o.id}</td>
                                  <td>
                                    <img
                                      src={imageUrl + o.product.image}
                                      style={{ width: "60px", height: "60px" }}
                                    />
                                  </td>
                                  <td>{o.product.name}</td>
                                  <td>
                                    {o.product.price.toLocaleString("it-IT", {
                                      style: "currency",
                                      currency: "VND",
                                    })}
                                  </td>
                                  <td>
                                    {o.price.toLocaleString("it-IT", {
                                      style: "currency",
                                      currency: "VND",
                                    })}
                                  </td>
                                  <td>{o.quantity}</td>
                                </tr>
                              ))}
                            </tbody>
                          </table>
                        </div>
                      </div>
                      {/* Item */}
                    </div>
                  </div>
                </div>
              </div>

              {/* Item */}
              <div className="col-4">
                <div className="card h-100">
                  <div className="card-body">
                    <h4 className="card-title mb-4 fw-bold">
                      <i class="fas fa-play"></i> Sản phẩm bán chạy nhất
                    </h4>
                    {bestSellingProduct.map((p, index) => (
                      <div className="row my-2" key={p.id}>
                        <div className="col-12 text-truncate text-dark">
                          <h6>
                            {index + 1}. {p.name}
                          </h6>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </>
      )}
    </>
  );
};