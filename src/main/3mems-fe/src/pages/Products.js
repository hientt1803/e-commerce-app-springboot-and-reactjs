import { faPenAlt, faTrashAlt } from "@fortawesome/free-solid-svg-icons";
import {
  Button,
  Form,
  Toast,
  ToastBody,
  ToastHeader,
} from "@themesberg/react-bootstrap";
import React, { useEffect, useState } from "react";
import DataTable from "react-data-table-component";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const ROOT_URL = "http://localhost:8080/admin/products";

const imageUrl = "http://localhost:8080/img/product";

export default () => {
  // Main variable
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [id, setID] = useState("");
  const [name, setName] = useState("");
  const [image, setImage] = useState("");
  const [price, setPrice] = useState(1);
  const [createDate, setcreateDate] = useState(new Date());
  const [available, setAvailable] = useState(true);
  const [quantity, setQuantity] = useState(1);
  const [productCategory, setProductCategory] = useState([]);

  // Variable for action
  const [showToast, setShowToast] = useState(false);
  const [messageModal, setMessageModal] = useState("");
  const [loading, setIsLoading] = useState(false);
  const [isValidForm, setIsValidForm] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [filteredProducts, setFilteredProducts] = useState([]);
  const [isUpdate, setIsUpdate] = useState(false);

  const [product, setProduct] = useState({
    name: "",
    image: "",
    price: 1,
    createDate: "",
    available: true,
    quantity: 1,
    categories: null,
  });

  //   Columns Data
  const columns = [
    {
      name: "ID",
      selector: (row) => row.id,
    },
    {
      name: "Tên sản phẩm",
      selector: (row) => row.name,
      sortable: true,
    },
    {
      name: "Hình ảnh",
      selector: (row) => (
        <img
          src={`${imageUrl}/` + row.image}
          style={{ width: "50px", height: "50px" }}
          alt={row.image}
        />
      ),
    },
    {
      name: "Giá sản phẩm",
      selector: (row) => row.price,
      sortable: true,
    },
    {
      name: "Ngày tạo",
      selector: (row) => row.createDate,
      sortable: true,
    },
    {
      name: "Số lượng",
      selector: (row) => row.quantity,
      sortable: true,
    },
    {
      name: "Trạng thái",
      selector: (row) => displayStatus(row.available),
      sortable: true,
    },
    {
      button: true,
      name: "Thao tác",
      cell: (row) => (
        <div className="d-flex">
          <Button
            variant="outline-tertiary"
            onClick={() => {
              handleGetProduct(row);
            }}
            className="m-1"
          >
            <FontAwesomeIcon icon={faPenAlt} />
          </Button>
          <Button variant="outline-danger" className="m-1">
            <FontAwesomeIcon icon={faTrashAlt} />
          </Button>
        </div>
      ),
    },
  ];

  const fetchProductData = async () => {
    const response = await fetch(`${ROOT_URL}`);
    const data = await response.json();
    const newProduct = data;

    try {
      if (filteredProducts.length === 0) {
        setFilteredProducts(newProduct);
      }
      setProducts(newProduct);
    } catch (error) {
      console.log("Error: " + error);
    }
  };

  const fetchCategoriesData = async () => {
    const response = await fetch("http://localhost:8080/admin/categories");
    const data = await response.json();

    const newCategories = data;
    try {
      setCategories(newCategories);
    } catch (error) {
      console.log("Error: " + error);
    }
  };

  const handleGetProduct = (row) => {
    console.log("hi");
    console.log(row);

    setName(row.name);
    setImage(row.image);
    setPrice(row.price);
    setQuantity(row.quantity);

    setProduct(row);
    console.log(product);
  };

  const displayStatus = (status) => {
    return status ? "Hoạt động" : "Ẩn";
  };

  const handleAddProduct = async (e) => {
    e.preventDefault();
    console.log("hello");
    console.log(product);
  };

  const handleSearch = () => {
    // Perform the search logic based on the searchTerm value
    const filteredData = products.filter((product) => {
      const name = product.name.toLowerCase();
      const searchValue = searchTerm.toLowerCase();
      return name.includes(searchValue);
    });

    // Update the data displayed in the table
    setFilteredProducts(filteredData);
  };

  //   FetchData
  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoading(true);
        await Promise.all([fetchProductData(), fetchCategoriesData()]);
        setIsLoading(false);
      } catch (error) {
        console.log("error: " + error);
      }
    };

    fetchData();
  }, []);

  useEffect(() => {
    const timeout = setTimeout(() => {
      setShowToast(false);
      setMessageModal("");
    }, 10000);
    return () => clearTimeout(timeout);
  }, [showToast]);

  return (
    <>
      {/* Toast */}
      <Toast
        show={showToast}
        onClose={() => setShowToast(false)}
        className="position-absolute end-0"
      >
        <ToastHeader>
          <i className="fa-solid fa-exclamation"></i>
          <strong className="me-auto ms-2 fw-bolder">Tin nhắn hệ thống</strong>
        </ToastHeader>
        <ToastBody>
          <span>{messageModal}</span>
        </ToastBody>
      </Toast>

      {loading ? (
        <h1 className="text-center">Loading...</h1>
      ) : (
        <>
          {/* Form section*/}
          

          {/* Table section */}
          <div className="container-fluid mt-5">
            <h3 className="fw-bolder">Danh sách sản phẩm</h3>

            {/* Searching input */}
            <div className="search-section d-flex">
              <div className="d-flex align-items-center mb-3">
                <input
                  type="text"
                  className="form-control me-2"
                  placeholder="Tìm kiếm theo tên..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />
                <button
                  className="btn btn-outline-primary"
                  onClick={() => handleSearch()}
                >
                  Search
                </button>
              </div>
            </div>

            {/* Table area */}
            <DataTable
              columns={columns}
              data={filteredProducts}
              responsive
              pagination
              striped
            />
          </div>
        </>
      )}
    </>
  );
};
