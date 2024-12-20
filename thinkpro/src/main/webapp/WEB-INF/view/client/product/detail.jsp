<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title> ${product.name} - Laptopshop</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

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
    <style>
        .page-link.disabled {
            color: var(--bs-pagination-disabled-color);
            pointer-events: none;
            background-color: var(--bs-pagination-disabled-bg);
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
    </style>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.css" rel="stylesheet">
</head>

<body>

<!-- Spinner Start -->
<div id="spinner"
     class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
    <div class="spinner-grow text-primary" role="status"></div>
</div>
<!-- Spinner End -->

<jsp:include page="../layout/header.jsp"/>

<!-- Single Product Start -->
<div class="container-fluid py-5 mt-5">
    <div class="container py-5">
        <div class="row g-4 mb-5">
            <div>
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="/">Home</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Chi Tiết Sản Phẩm
                        </li>
                    </ol>
                </nav>
            </div>
            <div class="col-lg-8 col-xl-9">
                <div class="row g-4">
                    <div class="col-lg-6 text-center">
                        <div class="border rounded">
                            <div id="carouselId" class="carousel slide position-relative" data-bs-ride="carousel">
                                <div class="carousel-inner" role="listbox">
                                    <c:forEach var="image" items="${product.images}" varStatus="status">
                                        <div class="carousel-item ${status.first ? 'active' : ''}">
                                            <img src="/images/products/${image.url}"
                                                 class="img-fluid bg-secondary rounded" alt="Slide ${status.index + 1}">
                                        </div>
                                    </c:forEach>
                                </div>
                                <button class="carousel-control-prev" type="button" data-bs-target="#carouselId"
                                        data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Previous</span>
                                </button>
                                <button class="carousel-control-next" type="button" data-bs-target="#carouselId"
                                        data-bs-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Next</span>
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <h4 class="fw-bold mb-3"> ${product.name}</h4>
                        <p class="mb-3">${product.category.name}</p>
                        <p class="mb-3">Số lượng còn lại : ${product.quantity}</p>
                        <div class="product-price">
                            <h5 class="original-price"><fmt:formatNumber type="number"
                                                                         value="${product.price}"/> đ</h5>
                            <h5 class="discounted-price"><fmt:formatNumber type="number"
                                                                           value="${product.price - (product.discount * product.price / 100)}"/>
                                đ</h5>
                        </div>
                        <div class="d-flex mb-4">
                            <c:set var="fullStars" value="${rate div 1}"/>
                            <c:set var="halfStar" value="${rate % 1 > 0 ? 1 : 0}"/>
                            <c:set var="emptyStars" value="${5 - fullStars - halfStar}"/>

                            <c:forEach var="i" begin="1" end="${fullStars}">
                                <i class="fa fa-star text-secondary"></i>
                            </c:forEach>

                            <!-- Half star -->
                            <c:if test="${halfStar == 1}">
                                <i class="fa fa-star-half-alt text-secondary"></i>
                            </c:if>

                            <!-- Empty stars -->
                            <c:forEach var="i" begin="1" end="${emptyStars}">
                                <i class="fa fa-star text-secondary"></i>
                            </c:forEach>
                        </div>


                        <div class="input-group quantity mb-5" style="width: 100px;">
                            <div class="input-group-btn">
                                <button class="btn btn-sm btn-minus rounded-circle bg-light border">
                                    <i class="fa fa-minus"></i>
                                </button>
                            </div>
                            <input type="text"
                                   class="form-control form-control-sm text-center border-0" value="1"
                                   data-cart-detail-index="0" id="quantityProduct"
                            >
                            <div class="input-group-btn">
                                <button class="btn btn-sm btn-plus rounded-circle bg-light border">
                                    <i class="fa fa-plus"></i>
                                </button>
                            </div>
                        </div>
                        <!-- <form action="/add-product-from-view-detail" method="post"
                            modelAttribute="product"> -->
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input class="form-control d-none" type="text" value="${product.productId}"
                               name="id"/>

                        <input class="form-control d-none" type="text" name="quantity"
                               id="cartDetails0.quantity" value="1"/>
                        <button data-product-id="${product.productId}"
                                class="btnAddToCartDetail btn border border-secondary rounded-pill px-4 py-2 mb-4 text-primary">
                            <i
                                    class="fa fa-shopping-bag me-2 text-primary"></i>
                            Add to cart
                        </button>
                        <!-- </form> -->

                    </div>
                    <div class="col-lg-12">
                        <nav>
                            <div class="nav nav-tabs mb-3">
                                <button class="nav-link active border-white border-bottom-0" type="button" role="tab"
                                        id="nav-about-tab" data-bs-toggle="tab" data-bs-target="#nav-about"
                                        aria-controls="nav-about" aria-selected="true">Chi tiết
                                </button>

                            </div>
                        </nav>
                        <div class="tab-content">
                            <div class="tab-pane active ps-2" id="nav-about" role="tabpanel"
                                 aria-labelledby="nav-about-tab">
                                <p>${product.description}</p>
                            </div>


                        </div>
                    </div>
                    <div class="col-lg-12">
                        <nav>
                            <div class="nav nav-tabs mb-3">

                                <button class="nav-link active border-white border-bottom-0" type="button" role="tab"
                                        id="nav-mission-tab" data-bs-toggle="tab" data-bs-target="#nav-mission"
                                        aria-controls="nav-mission" aria-selected="false">Đánh giá
                                </button>
                            </div>
                        </nav>
                        <div class="tab-content mb-5">

                            <div class="tab-pane active" id="nav-mission" role="tabpanel"
                                 aria-labelledby="nav-mission-tab">
                                <c:forEach var="feedback" items="${feedbacks}">
                                    <div class="d-flex me-2 row">
                                        <img src="/images/avatar.webp" class="img-fluid rounded-circle p-3 col-2"
                                             style="width: 100px; height: 100px;" alt="">
                                        <div class="col-10">
                                            <p class="mb-2" style="font-size: 14px;">
                                                <fmt:formatDate value="${feedback.getCreateDate()}"
                                                                pattern="dd/MM/yyyy"/>
                                            </p>
                                            <div class="d-flex justify-content-between">
                                                <h5>${feedback.createBy}</h5>
                                                <div class="d-flex mb-3 ">
                                                    <c:forEach var="i" begin="1" end="${feedback.rate}">
                                                        <i class="fa fa-star text-secondary"></i>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <p>${feedback.content} </p>
                                        </div>
                                    </div>
                                    <hr>
                                </c:forEach>
                                <c:if test="${totalPages > 0}">
                                    <div class="pagination d-flex justify-content-center mt-5">
                                        <li class="page-item">
                                            <a class="${1 eq currentPage ? 'disabled page-link' : 'page-link'}"
                                               href="/product/${product.productId}?pageNum=${currentPage - 1}" aria-label="Previous">
                                                <span aria-hidden="true">&laquo;</span>
                                            </a>
                                        </li>
                                        <c:forEach begin="0" end="${totalPages - 1}" varStatus="loop">
                                            <li class="page-item">
                                                <a class="${(loop.index+1 ) eq (currentPage) ? 'active page-link' : 'page-link'}"
                                                   href="/product/${product.productId}?pageNum=${loop.index + 1}">
                                                        ${loop.index + 1}
                                                </a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item">
                                            <a class="${totalPages eq currentPage ? 'disabled page-link' : 'page-link'}"
                                               href="/product/${product.productId}?pageNum=${currentPage + 1}" aria-label="Next">
                                                <span aria-hidden="true">&raquo;</span>
                                            </a>
                                        </li>
                                    </div>
                                </c:if>

                            </div>

                        </div>
                    </div>

                </div>
            </div>
            <div class="col-lg-4 col-xl-3">
                <div class="row g-4 fruite">
                    <div class="col-lg-12">

                        <div class="mb-4">
                            <h4>Danh mục</h4>
                            <ul class="list-unstyled fruite-categorie">
                                <c:forEach var="category" items="${categories}">
                                    <li>
                                        <div class="d-flex justify-content-between fruite-name">
                                            <a href="/home?category=${category.name}">${category.name}</a>
                                            <span>(${category.products.size()})</span>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<!-- Single Product End -->

<jsp:include page="../layout/footer.jsp"/>


<!-- Back to Top -->
<a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i
        class="fa fa-arrow-up"></i></a>


<!-- JavaScript Libraries -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="/client/lib/easing/easing.min.js"></script>
<script src="/client/lib/waypoints/waypoints.min.js"></script>
<script src="/client/lib/lightbox/js/lightbox.min.js"></script>
<script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

<script src="/client/js/main.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.js"></script>

</body>

</html>