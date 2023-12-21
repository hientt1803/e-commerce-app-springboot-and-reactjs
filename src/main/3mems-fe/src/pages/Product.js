import { faPenAlt, faTrashAlt } from "@fortawesome/free-solid-svg-icons";
import {
  Button,
  ButtonGroup,
  Form,
  Modal,
  ModalBody,
  ModalFooter,
  Toast,
  ToastBody,
  ToastHeader,
} from "@themesberg/react-bootstrap";
import React, { useEffect, useState } from "react";
import DataTable from "react-data-table-component";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import ModalHeader from "@themesberg/react-bootstrap/lib/esm/ModalHeader";

const ROOT_URL = "http://localhost:8080/admin/products";

const imageUrl = "http://localhost:8080/img/product";

export default () => {
  // Main Variable
  const [products, setProducts] = useState([]);
  const [category, setCategory] = useState();
  const [categories, setCategories] = useState([]);

  // Action Variable
  const [isLoading, setIsLoading] = useState(false);
  const [isShowToast, setIsShowToast] = useState(false);
  const [messageToast, setMessageToast] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [modalsValue, setModalValue] = useState("");
  const handleCloseModal = () => setShowModal(false);
  const [showCardForm, setShowCardForm] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [filteredProducts, setFilteredProducts] = useState([]);
  const [selectedRow, setSelectedRow] = useState(null);
  const [selectedUpdateProduct, setSelectedUpdateProduct] = useState(null);

  // Form Variable
  const [productId, setProductID] = useState("");
  const [productName, setProductName] = useState("");
  const [productImage, setProductImage] = useState(null);
  const [productPrice, setProductPrice] = useState(1);
  const [createDate, setcreateDate] = useState(new Date());
  const [available, setAvailable] = useState(true);
  const [productQuantity, setProductQuantity] = useState(1);
  const [productCategory, setProductCategory] = useState("");
  const [isUpdate, setIsUpdate] = useState(false);

  // Form error
  const [errorName, setErrorName] = useState("");
  const [errorImage, setErrorImage] = useState(null);
  const [errorPrice, setErrorPrice] = useState("");
  const [errorQuantity, setErrorQuantity] = useState("");
  const [errorCategory, setErrorCategory] = useState("");

  const [product, setProduct] = useState({
    id: "",
    name: "",
    image: "",
    price: 1,
    createDate: "",
    available: true,
    quantity: 1,
    category: {},
  });

  // DataTable Variable
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
      name: "Danh mục",
      selector: (row) => row.category.id,
      sortable: true,
    },
    {
      name: "Trạng thái",
      selector: (row) => displayAvailable(row.available),
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
              console.log(row);
              handleGetProductDetails(row);
            }}
            className="m-1"
          >
            <FontAwesomeIcon icon={faPenAlt} />
          </Button>

          <Button
            variant="outline-danger"
            className="m-1"
            onClick={() => {
              console.log(row);
              handleOpenModal(row);
            }}
          >
            <FontAwesomeIcon icon={faTrashAlt} />
          </Button>
        </div>
      ),
    },
  ];

  const conditionalRowStyles = [
    {
      when: (row) => row.available === false,

      style: (row) => ({ backgroundColor: row.available ? "inerit" : "pink" }),
    },
    {
      when: (row) => row.id === product.id,

      style: (row) => (
        { border: "1px grey solid" }, { backgroundColor: "rgb(109, 235, 198)" }
      ),
    },
  ];

  const displayAvailable = (status) => {
    return status ? "Hoạt động" : "Ẩn";
  };

  // Fetch Function
  const fetchProductData = async () => {
    try {
      const response = await fetch(ROOT_URL);
      const data = await response.json();
      const newProduct = data;
      {
        if (filteredProducts.length === 0) {
          setFilteredProducts(newProduct);
        }
        console.log(newProduct);
        setProducts(newProduct);
        setFilteredProducts(newProduct);
      }
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
      console.log(newCategories);
    } catch (error) {
      console.log("Error: " + error);
    }
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

  // Util function
  function formatDate(dateString) {
    var date = new Date(dateString);

    var year = date.getFullYear();
    var month = ("0" + (date.getMonth() + 1)).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);

    var formattedDate = year + "-" + month + "-" + day;

    return formattedDate;
  }

  // Form action
  const handleAddProduct = async (e) => {
    e.preventDefault();

    if (productImage === null) {
      setErrorImage("Vui lòng chọn hình ảnh sản phẩm.");
      return;
    } else {
      setErrorImage("");
    }

    Object.assign(product, {
      available: available,
      createDate: formatDate(createDate),
      image: productImage.name,
      name: productName,
      price: Number(productPrice),
      quantity: Number(productQuantity),
    });

    console.log(JSON.stringify(product));

    if (!isFormValid(product)) {
      return;
    }

    const formData = new FormData();
    formData.append("product", JSON.stringify(product));
    formData.append("file", productImage);
    console.log(productImage);

    try {
      const resp = await fetch(ROOT_URL, {
        method: "POST",
        body: formData,
      });
      const data = await resp.json();
      setMessageToast("Thêm sản phẩm thành công!");
      setIsShowToast(true);
      handleResetForm();
      fetchProductData();
    } catch (error) {
      console.log(error);
      setMessageToast("Thêm sản phẩm thất bại!");
      setIsShowToast(true);
    }

    console.log("Product have been added");
  };

  const handleUpdateProduct = async (e) => {
    e.preventDefault();

    Object.assign(product, {
      available: available,
      createDate: formatDate(createDate),
      image: productImage.name,
      name: productName,
      price: Number(productPrice),
      quantity: Number(productQuantity),
    });

    console.log(JSON.stringify(product));

    if (!isFormValid(product)) {
      return;
    }

    const formData = new FormData();
    formData.append("product", JSON.stringify(product));
    formData.append("file", productImage);
    console.log(productImage);

    if (productImage === null) {
      if (productImage === null) {
        setErrorImage("Vui lòng chọn hình ảnh sản phẩm.");
        return;
      } else {
        setErrorImage("");

        try {
          const resp = await fetch(ROOT_URL, {
            method: "PUT",
            body: formData,
          });
          const data = await resp.json();
          setMessageToast("Cập nhật sản phẩm thành công");
          setIsShowToast(true);
          setShowModal(false);
          handleResetForm();
        } catch (error) {
          console.log(error);
          setMessageToast("Lỗi khi cập nhật sản phẩm:", error);
          setIsShowToast(true);
        }
      }
    } else {
      setErrorImage("");

      Object.assign(product, {
        image: productImage,
      });

      try {
        const resp = await fetch(`${ROOT_URL}/noneMultipart`, {
          method: "PUT",
          body: formData,
        });
        const data = await resp.json();
        setMessageToast("Cập nhật sản phẩm thành công");
        setIsShowToast(true);
        setShowModal(false);
        handleResetForm();
      } catch (error) {
        console.log(error);
        setMessageToast("Lỗi khi cập nhật sản phẩm:", error);
        setIsShowToast(true);
      }
    }

    console.log("Product have been updated");
  };

  const handleOpenModal = (row) => {
    setSelectedRow(row);
    setModalValue(row.name);
    setShowModal(true);
  };

  const handleUpdateAvailable = () => {
    if (selectedRow) {
      const updatedProduct = {
        ...selectedRow,
        available: !selectedRow.available,
      };

      fetch(`${ROOT_URL}/${selectedRow.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(updatedProduct),
      })
        .then((response) => {
          if (response.ok) {
            const data = response.json();
            console.log(data);
            // Xử lý thành công
            setMessageToast("Trạng thái đã được cập nhật thành công");
            setIsShowToast(true);
            setShowModal(false);
            fetchProductData();
          } else {
            // Xử lý lỗi
            setMessageToast(
              "Lỗi khi cập nhật trạng thái:",
              response.statusText
            );
            setIsShowToast(true);
          }
        })
        .catch((error) => {
          // Xử lý lỗi
          setMessageToast("Lỗi khi cập nhật trạng thái:", error);
          setIsShowToast(true);
        });
    }
  };

  const handleGetProductDetails = (row) => {
    setIsUpdate(true);
    setSelectedUpdateProduct(row);

    Object.assign(product, {
      id: row.id,
    });

    setProductName(row.name);
    setProductImage(row.image);
    setProductPrice(row.price);
    setProductQuantity(row.quantity);
    setProductCategory(row.category.id);
    setAvailable(row.available);
    setcreateDate(row.createDate);

    const fileName = document.getElementsByName("file");
    fileName.values = row.image;
    console.log(fileName);

    setProduct(row);
  };

  const handleResetForm = () => {
    setProductID("");
    setProductName("");
    setProductImage(null);
    setProductPrice("1");
    setProductQuantity("1");
    setProductCategory("");
    setAvailable(true);
    setcreateDate(formatDate(new Date()));

    setProduct({});
    setIsUpdate(false);
  };

  const handleOnChangeSelect = (e) => {
    console.log(e.target.value);

    const category = categories.find((item) => item.id === e.target.value);
    setProductCategory(e.target.value);
    Object.assign(product, {
      category: category,
    });
    console.log(product);
  };

  const handleOnChangeFile = (e) => {
    setProductImage(e.target.files[0]);
  };

  const isFormValid = (product) => {
    let isValid = true;

    if (product.name.trim().length === 0) {
      setErrorName("Vui lòng nhập tên sản phẩm.");
      isValid = false;
    } else {
      setErrorName("");
    }

    if (product.price <= 0) {
      setErrorPrice("Vui lòng nhập giá sản phẩm hợp lệ.");
      isValid = false;
    } else {
      setErrorPrice("");
    }

    if (product.quantity <= 0) {
      setErrorQuantity("Vui lòng nhập số lượng sản phẩm hợp lệ.");
      isValid = false;
    } else {
      setErrorQuantity("");
    }

    if (productCategory.length === 0) {
      setErrorCategory("Vui lòng chọn danh mục sản phẩm.");
      isValid = false;
    } else {
      setErrorCategory("");
    }

    return isValid;
  };

  //   Use Effect
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
      setIsShowToast(false);
      setMessageToast("");
    }, 7000);
    return () => clearTimeout(timeout);
  }, [isShowToast]);

  useEffect(() => {
    console.log(productImage);
    console.log(product);
  }, [productImage, product]);

  return (
    <>
      <div className="container-fluid position-relative">
        {/* Toast */}
        <Toast
          show={isShowToast}
          onClose={() => setIsShowToast(false)}
          className="position-fixed end-0"
          style={{ "z-index": "1000", top: "5px" }}
        >
          <ToastHeader>
            <span className="fw-bolder">|</span>
            <strong className="me-auto ms-2 fw-bolder">
              4MEMS - Thông báo
            </strong>
          </ToastHeader>
          <ToastBody>
            <span>{messageToast}</span>
          </ToastBody>
        </Toast>

        {/* Modal */}
        <Modal
          as={Modal.Dialog}
          centered
          show={showModal}
          onHide={handleCloseModal}
        >
          <Modal.Header>
            <Modal.Title className="h6 text-muted">
              Cập nhật sản phẩm:
              <span className="fw-bold text-dark">{" " + modalsValue}</span>
            </Modal.Title>
            <Button
              variant="close"
              aria-label="Close"
              onClick={handleCloseModal}
            />
          </Modal.Header>
          <ModalBody>
            <img src={"http://localhost:8080/img/cat-delete.jpg"} alt="" />
          </ModalBody>
          <ModalFooter>
            <div className="d-flex">
              <Button
                variant="outline-primary"
                className="m-1"
                onClick={() => handleCloseModal()}
              >
                Trở lại
              </Button>
              <Button
                variant="danger"
                className="m-1"
                onClick={handleUpdateAvailable}
              >
                Cập nhật trạng thái
              </Button>
            </div>
          </ModalFooter>
        </Modal>

        {/* Form Overview */}
        <h2 className="my-5 text-center fw-bolder">Quản lý sản phẩm</h2>
        <Form className="row">
          {/* Item */}
          <Form.Group className="mb-3 col-6">
            <Form.Label>Tên sản phẩm</Form.Label>
            <Form.Control
              onChange={(e) => {
                setProductName(e.target.value);
              }}
              required
              type="text"
              value={productName}
            />
            {errorName && <i className="text-danger">{errorName}</i>}
          </Form.Group>
          {/* Item */}
          <Form.Group className="mb-3 col-6">
            <Form.Label>Hình ảnh sản phẩm</Form.Label>
            <Form.Control
              onChange={handleOnChangeFile}
              required
              type="file"
              name="file"
              accept="image/*"
              // value={''}
            />
            {errorImage && <i className="text-danger">{errorImage}</i>}
          </Form.Group>
          {/* Item */}
          <Form.Group className="mb-3 col-6">
            <Form.Label>Giá sản phẩm</Form.Label>
            <Form.Control
              onChange={(e) => {
                setProductPrice(e.target.value);
              }}
              required
              type="number"
              value={productPrice}
            />
            {errorPrice && <i className="text-danger">{errorPrice}</i>}
          </Form.Group>
          {/* Item */}
          <Form.Group className="mb-3 col-6">
            <Form.Label>Số lượng sản phẩm</Form.Label>
            <Form.Control
              onChange={(e) => {
                setProductQuantity(e.target.value);
              }}
              required
              value={productQuantity}
              type="number"
            />
            {errorQuantity && <i className="text-danger">{errorQuantity}</i>}
          </Form.Group>
          {/* Item */}
          <Form.Group className="mb-3 col-6">
            <Form.Label>Danh mục sản phẩm</Form.Label>
            <Form.Select
              defaultValue={productCategory}
              onChange={handleOnChangeSelect}
              value={productCategory}
            >
              <option>Danh mục</option>
              {categories.map((ct) => (
                <option key={ct.id} value={ct.id}>
                  {ct.name}
                </option>
              ))}
            </Form.Select>
            {errorCategory && <i className="text-danger">{errorCategory}</i>}
          </Form.Group>
          <div className="d-flex">
            <Button
              variant="tertiary"
              className="m-1"
              onClick={(e) => handleAddProduct(e)}
              disabled={isUpdate}
            >
              Thêm mới
            </Button>
            <Button
              variant="info"
              className="m-1"
              onClick={(e) => handleUpdateProduct(e)}
            >
              Cập nhật
            </Button>
            <Button
              type="reset"
              variant="primary"
              className="m-1"
              onClick={() => handleResetForm()}
            >
              Làm mới
            </Button>
          </div>
        </Form>

        {/* Table Overview */}
        <h3 className="fw-bolder mt-5">Danh sách sản phẩm</h3>

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

        {isLoading ? (
          <h2 className="text-center bg-white">Loading...</h2>
        ) : (
          <>
            <DataTable
              // columns={columns.map((column) => ({
              //   ...column,
              //   cell: (row) =>
              //     column.name === "Trạng thái" ? (
              //       <Button
              //         variant="outline-info"
              //         className="m-1"
              //         onClick={() => handleUpdateAvailable(row)}
              //       >
              //         <i className="fa fa-refresh" aria-hidden="true"></i>
              //       </Button>
              //     ) : (
              //       column.cell(row)
              //     ),
              // }))}
              columns={columns}
              data={filteredProducts}
              responsive
              highlightOnHover
              pagination
              striped
              conditionalRowStyles={conditionalRowStyles}
            />
          </>
        )}
      </div>
    </>
  );
};
