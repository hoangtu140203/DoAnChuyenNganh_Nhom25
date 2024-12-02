<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Navbar start -->
<div class="container-fluid fixed-top">
    <div class="container px-0">
        <nav class="navbar navbar-light bg-white navbar-expand-xl">
            <a href="/" class="navbar-brand">
                <h1 class="text-primary display-6">ThinkPro</h1>
            </a>

            <div class="collapse navbar-collapse bg-white justify-content-between mx-5" id="navbarCollapse">
                <div class="mx-auto">
                    <form class="d-flex w-200">
                        <input class="form-control mr-sm-2 search-input" type="search" placeholder="Search"
                               aria-label="Search">
                        <button class="btn-search btn border border-secondary btn-md-square rounded-circle bg-white ms-2"
                                data-bs-toggle="modal" data-bs-target="#searchModal"><i
                                class="fas fa-search text-primary"></i></button>
                    </form>
                </div>
                <div class="d-flex m-3 me-0">
                    <c:if test="${not empty pageContext.request.userPrincipal}">
                        <a href="/cart" class="position-relative me-4 my-auto">
                            <i class="fa fa-shopping-bag fa-2x"></i>
                            <span
                                    class="position-absolute bg-secondary rounded-circle d-flex align-items-center justify-content-center text-dark px-1"
                                    style="top: -5px; left: 15px; height: 20px; min-width: 20px;" id="sumCart">
                                    ${sessionScope.sum}
                            </span>
                        </a>
                        <div class="dropdown my-auto">
                            <a href="#" class="dropdown" role="button" id="dropdownMenuLink"
                               data-bs-toggle="dropdown" aria-expanded="false" data-bs-toggle="dropdown"
                               aria-expanded="false">
                                <i class="fas fa-user fa-2x"></i>
                            </a>

                            <ul class="dropdown-menu dropdown-menu-end p-4" aria-labelledby="dropdownMenuLink">
                                <li class="d-flex align-items-center flex-column" style="min-width: 300px;">
                                    <div class="text-center my-3">
                                        <c:out value="${sessionScope.fullname}"/>
                                    </div>
                                </li>

                                <li><a class="dropdown-item" href="/client/user">Quản lý tài khoản</a></li>

                                <li><a class="dropdown-item" href="/order-history">Lịch sử mua hàng</a></li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li>
                                    <form method="post" action="/logout">
                                        <input type="hidden" name="${_csrf.parameterName}"
                                               value="${_csrf.token}"/>
                                        <button class="dropdown-item">Đăng xuất</button>
                                    </form>
                                </li>
                            </ul>
                        </div>
                    </c:if>
                    <c:if test="${empty pageContext.request.userPrincipal}">
                        <a href="/login" class="a-login position-relative me-4 my-auto">
                            Đăng nhập
                        </a>
                    </c:if>
                </div>
            </div>
        </nav>
    </div>
</div>


<style>
    .search-input {
        width: 300px;
        max-width: 100%;
    }

    @media (max-width: 768px) {
        .search-input {
            width: 200px;
        }
    }

    .position-relative .position-absolute {
        top: 50%;
        transform: translateY(-50%);
    }
</style>
