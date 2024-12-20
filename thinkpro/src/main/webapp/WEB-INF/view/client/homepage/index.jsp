<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chủ - Laptopshop</title>

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
          rel="stylesheet">

    <!-- Icon Font Stylesheet -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
    <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="/client/lib/bootstrap/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="/client/css/style.css" rel="stylesheet">

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.css" rel="stylesheet">
    <style>
        .page-link.disabled {
            color: var(--bs-pagination-disabled-color);
            pointer-events: none;
            background-color: var(--bs-pagination-disabled-bg);
        }

        .product-card {
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            height: 100%;
        }

        .product-card .product-details {
            flex-grow: 1;
        }

        .product-card .add-to-cart {
            text-align: center;
            margin-top: auto;
            padding-bottom: 20px; /* Adjust this value to move the button higher */
        }

        .product-price {
            text-align: center;
        }

        .product-price .original-price {
            text-decoration: line-through;
            color: grey;
            display: block;
        }

        .product-price .discounted-price {
            color: red;
            display: block;
        }

        .search-filter {
            margin-bottom: 30px;
        }

        .search-filter .form-group {
            margin-bottom: 20px;
        }

        .abovepage {
            position: fixed; /* Stays fixed as the page scrolls */
            bottom: 80px; /* Places it above the "Back to Top" button */
            right: 20px; /* Aligns it to the right */
            z-index: 1050; /* Ensures it appears above other elements, including "Back to Top" */
            padding: 10px; /* Adds padding inside the widget */
        }
    </style>
</head>
<body>

<!-- Spinner Start -->
<div id="spinner"
     class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center">
    <div class="spinner-grow text-primary" role="status"></div>
</div>
<!-- Spinner End -->

<jsp:include page="../layout/header.jsp"/>
<jsp:include page="../layout/banner.jsp"/>

<div class="container">
    <div class="row search-filter ps-5 pe-5">
        <form id="searchFilterForm" class="row align-items-center" >
            <div class="col-md-3 col-sm-6 form-group">
                <label for="category" class="form-label">Danh mục</label>
                <select class="form-select" id="category" name="category">
                    <option value=" ">Chọn danh mục</option>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.name}"
                                <c:if test="${category.name == param.category}">selected</c:if>>${category.name}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Sorting Radios -->
            <div class="col-md-3 col-sm-6 form-group">
                <label class="form-label d-block">Sắp xếp theo giá</label>
                <div class="d-flex flex-column">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="sortBy" id="sortDefault" value=" "
                               <c:if test="${param.sortBy == ' '}">checked</c:if>>
                        <label class="form-check-label" for="sortDefault">Mặc định</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="sortBy" id="sortAsc" value="asc"
                               <c:if test="${param.sortBy == 'asc'}">checked</c:if>>
                        <label class="form-check-label" for="sortAsc">Tăng dần</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="sortBy" id="sortDesc" value="desc"
                               <c:if test="${param.sortBy == 'desc'}">checked</c:if>>
                        <label class="form-check-label" for="sortDesc">Giảm dần</label>
                    </div>
                </div>
            </div>

            <!-- Price Range Radios -->
            <div class="col-md-4 col-sm-12 form-group">
                <label class="form-label d-block">Khoảng giá</label>
                <div class="d-flex flex-wrap">
                    <div class="form-check me-2">
                        <input class="form-check-input" type="radio" name="price" id="priceRangeDefault" value=" "
                               <c:if test="${param.price == ' '}">checked</c:if>>
                        <label class="form-check-label" for="priceRangeDefault">Mặc định</label>
                    </div>
                    <div class="form-check me-2">
                        <input class="form-check-input" type="radio" name="price" id="priceRange1" value="duoi-10-trieu"
                               <c:if test="${param.price == 'duoi-10-trieu'}">checked</c:if>>
                        <label class="form-check-label" for="priceRange1">Dưới 10 triệu</label>
                    </div>
                    <div class="form-check me-2">
                        <input class="form-check-input" type="radio" name="price" id="priceRange2" value="10-15-trieu"
                               <c:if test="${param.price == '10-15-trieu'}">checked</c:if>>
                        <label class="form-check-label" for="priceRange2">Từ 10-15 triệu</label>
                    </div>
                </div>
                <div class="d-flex flex-wrap">
                    <div class="form-check me-2">
                        <input class="form-check-input" type="radio" name="price" id="priceRange3" value="15-20-trieu"
                               <c:if test="${param.price == '15-20-trieu'}">checked</c:if>>
                        <label class="form-check-label" for="priceRange3">Từ 15-20 triệu</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="price" id="priceRange4" value="tren-20-trieu"
                               <c:if test="${param.price == 'tren-20-trieu'}">checked</c:if>>
                        <label class="form-check-label" for="priceRange4">Trên 20 triệu</label>
                    </div>
                </div>
            </div>

            <!-- Search Button -->
            <div class="col-md-2 col-sm-12 form-group d-flex align-items-end">
                <button type="submit" class="btn btn-primary w-100">Tìm kiếm</button>
            </div>
        </form>


    </div>

    <div class="row g-4 p-5 d-flex justify-content-around">
        <c:if test="${totalPages == 0}">
            <div>Không tìm thấy sản phẩm</div>
        </c:if>
        <c:forEach var="product" items="${products}">
            <div class="col-md-12 col-lg-3">
                <div class="rounded position-relative fruite-item border border-secondary rounded-bottom product-card">
                    <div class="fruite-img">
                        <img src="/images/products/${product.images[0].url}" class="img-fluid w-100 rounded-top" alt="">
                    </div>
                    <div class="text-white bg-secondary px-3 py-1 position-absolute"
                         style="top: -1px; right: -1px; border-radius: 50%;">
                        -<fmt:formatNumber type="number" value="${product.discount}"/>%
                    </div>
                    <div class="p-4 product-details text-center">
                        <h4 style="font-size: 15px;">
                            <a href="/product/${product.productId}">${product.name}</a>
                        </h4>
                        <div class="product-price">
                            <span class="original-price"><fmt:formatNumber type="number"
                                                                           value="${product.price}"/> đ</span>
                            <span class="discounted-price"><fmt:formatNumber type="number"
                                                                             value="${product.price - (product.discount * product.price / 100)}"/> đ</span>
                        </div>
                    </div>
                    <div class="add-to-cart">
                        <form action="/add-product-to-cart/${product.productId}" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button class="mx-auto btn border border-secondary rounded-pill px-3 text-primary">
                                <i class="fa fa-shopping-bag me-2 text-primary"></i>
                                Thêm giỏ hàng
                            </button>
                            <a href="/product/${product.productId}"
                               class="btn border border-secondary rounded-pill px-3 text-primary mt-2">
                                <i class="fa fa-eye me-2 text-primary"></i>
                                Chi tiết
                            </a>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
        <c:if test="${totalPages > 0}">
            <div class="pagination d-flex justify-content-center mt-5">
                <li class="page-item">
                    <a class="${1 eq currentPage ? 'disabled page-link' : 'page-link'}"
                       href="/home?pageNum=${currentPage - 1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <li class="page-item ${i eq currentPage ? 'active' : ''}">
                        <a class="page-link" href="/home?pageNum=${i}">${i}</a>
                    </li>
                </c:forEach>
                <li class="page-item">
                    <a class="${totalPages eq currentPage ? 'disabled page-link' : 'page-link'}"
                       href="/home?pageNum=${currentPage + 1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </div>
        </c:if>
    </div>
    <div>
        <jsp:include page="../layout/chatbotWidget.jsp"/>
    </div>
</div>

<jsp:include page="../layout/footer.jsp"/>

<!-- Back to Top -->
<a href="#" class="btn btn-primary py-3 fs-4 back-to-top"><i class="bi bi-arrow-up"></i></a>

<!-- JavaScript Libraries -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="/client/lib/easing/easing.min.js"></script>
<script src="/client/lib/waypoints/waypoints.min.js"></script>
<script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>
<script src="/client/lib/counterup/counterup.min.js"></script>
<script src="/client/lib/parallax/parallax.min.js"></script>
<script src="/client/lib/lightbox/js/lightbox.min.js"></script>

<!-- Template Javascript -->
<script src="/client/js/main.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="/client/js/main.js"></script>
<script>
    // Add your custom JavaScript here if needed
</script>
</body>
</html>
