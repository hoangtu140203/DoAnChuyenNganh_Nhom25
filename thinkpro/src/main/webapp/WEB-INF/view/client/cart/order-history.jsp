<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title> Lịch sử mua hàng - Laptopshop</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
            href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
            rel="stylesheet">

    <!-- Icon Font Stylesheet -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
          rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
    <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


    <!-- Customized Bootstrap Stylesheet -->
    <link href="/client/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="/client/css/style.css" rel="stylesheet">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <style>
        .star-rating {
            display: flex;
            gap: 5px;
            cursor: pointer;
        }

        .star-rating .bi-star {
            font-size: 2rem;
            color: #ddd;
        }

        .star-rating .bi-star.selected {
            color: gold;
        }
    </style>
</head>

<body>

<!-- Spinner Start -->
<div id="spinner"
     class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
    <div class="spinner-grow text-primary" role="status"></div>
</div>
<!-- Spinner End -->

<jsp:include page="../layout/header.jsp"/>

<!-- Cart Page Start -->
<div class="container-fluid py-5">
    <div class="container py-5">
        <div class="mb-3">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="/">Home</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Lịch sử mua hàng</li>
                </ol>
            </nav>
        </div>

        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Sản phẩm</th>
                    <th scope="col">Tên</th>
                    <th scope="col">Giá cả</th>
                    <th scope="col">Số lượng</th>
                    <th scope="col">Thành tiền</th>
                    <th scope="col">Trạng thái</th>
                    <th scope="col">Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${ empty orders}">
                    <tr>
                        <td colspan="6">
                            Không có đơn hàng nào
                        </td>
                    </tr>
                </c:if>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td colspan="2">Đơn hàng</td>
                        <td colspan="1">
                            <fmt:formatNumber type="number" value="${order.total}"/>
                            đ
                        </td>
                        <td colspan="2"></td>
                        <td colspan="1">
                            <c:if test="${order.status == 'PENDING'}">
                                Đơn hàng chưa được xác nhận
                            </c:if>
                            <c:if test="${order.status == 'WAITING'}">
                                Đơn hàng đang được giao
                            </c:if>
                            <c:if test="${order.status == 'CONFIRMED'}">
                                Đơn hàng đã được xác nhận
                            </c:if>
                            <c:if test="${order.status == 'COMPLETED'}">
                                Đơn hàng đã giao thành công
                            </c:if>
                            <c:if test="${order.status == 'CANCELLED'}">
                                Đơn hàng đã bị hủy
                            </c:if>
                        </td>
                        <td colspan="1">
                            <c:if test="${order.status == 'PENDING'}">
                                <form method="post" action="/cancel-bill/${order.billId}"
                                      style="margin: 0px; display: inline-block;">
                                    <input type="hidden" name="${_csrf.parameterName}"
                                           value="${_csrf.token}"/>
                                    <button class="btn btn-md rounded-circle bg-light border mt-4"
                                            style="width: 40px; height: 40px; margin: 1px;">
                                        <i class="fa fa-times text-danger"></i>
                                    </button>
                                </form>
                            </c:if>

                        </td>
                    </tr>

                    <c:forEach var="orderDetail" items="${order.billDetails}">
                        <tr>
                            <th scope="row">
                                <div class="d-flex align-items-center">
                                    <img src="/images/products/${orderDetail.product.images[0].url}"

                                         class="img-fluid me-5 rounded-circle"
                                         style="width: 80px; height: 80px;" alt="">
                                </div>
                            </th>
                            <td>
                                <p class="mb-0 mt-4">
                                    <a href="/product/${orderDetail.product.productId}" target="_blank">
                                            ${orderDetail.product.name}
                                    </a>
                                </p>
                            </td>
                            <td>
                                <p class="mb-0 mt-4">
                                    <fmt:formatNumber type="number" value="${orderDetail.price}"/>
                                    đ
                                </p>
                            </td>
                            <td>
                                <div class="input-group quantity mt-4" style="width: 100px;">
                                    <input type="text"
                                           class="form-control form-control-sm text-center border-0"
                                           value="${orderDetail.quantity}">
                                </div>
                            </td>
                            <td>
                                <p class="mb-0 mt-4" data-cart-detail-id="${cartDetail.id}">
                                    <fmt:formatNumber type="number"
                                                      value="${orderDetail.price * orderDetail.quantity}"/> đ
                                </p>
                            </td>
                            <td>

                            </td>
                            <td class="pt-4">
                                <c:if test="${order.status == 'COMPLETED' && orderDetail.feedback == false }">
                                    <a style="padding: 5px 0; width: 100px; " class="btn btn-primary text-white    "
                                       role="button"
                                       data-bs-target="#feedback-modal" data-toggle="modal"
                                       data-bs-toggle="modal" onclick="giveFeedback(${orderDetail.id})">Đánh giá</a>
                                </c:if>
                            </td>


                        </tr>
                    </c:forEach>
                </c:forEach>

                </tbody>
            </table>
        </div>

    </div>
</div>
<!-- Cart Page End -->

<div class="modal fade" id="feedback-modal" tabindex="-1" aria-labelledby="feedbackModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="feedbackModalLabel">Đánh giá</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="feedback-form">

                    <div class="mb-3">
                        <label for="feedback-content" class="form-label">Nội dung *</label>
                        <input type="text" class="form-control" id="feedback-content" name="feedback-content" required>
                    </div>
                    <div class="mb-3 ">
                        <label class="form-label ">Đánh giá sao *</label>
                        <div class="star-rating d-flex justify-content-center " id="star-rating">
                            <i class="bi bi-star" data="1"></i>
                            <i class="bi bi-star" data="2"></i>
                            <i class="bi bi-star" data="3"></i>
                            <i class="bi bi-star" data="4"></i>
                            <i class="bi bi-star" data="5"></i>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary text-white">Gửi đánh giá</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


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
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.js"></script>

<!-- Template Javascript -->
<script src="/client/js/main.js"></script>
<script>
    let selectedRating = 0;
    let currentBillId = null;

    function giveFeedback(billId) {
        currentBillId = billId;
        $('#feedback-modal').modal('show');
    }

    $(document).ready(function () {
        $('#star-rating i').on('click', function () {
            selectedRating = $(this).attr('data');
            $('#star-rating i').removeClass('selected');
            $(this).prevAll().addBack().addClass('selected');
        });

        $('#feedback-form').on('submit', function (e) {
            e.preventDefault();

            const feedbackContent = $('#feedback-content').val();
            const feedbackData = {
                content: feedbackContent,
                rate: selectedRating,
                billDetaiId: currentBillId
            };
            const token = $("meta[name='_csrf']").attr("content");
            const header = $("meta[name='_csrf_header']").attr("content");

            $.ajax({
                type: 'POST',
                url: `${window.location.origin}/api/give-feedback`,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                contentType: 'application/json',
                data: JSON.stringify(feedbackData),
                success: function (response) {

                    $('#feedback-modal').modal('hide');
                    location.reload();
                },
                error: function (error) {
                    console.error('Error:', error);
                    alert('Có lỗi xảy ra. Vui lòng thử lại.');
                }
            });
        });
    });
</script>
</body>

</html>