<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="DACN - Dự án laptopshop" />
    <meta name="author" content="DACN" />
    <title>ADMIN</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <link href="/admin/css/category.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>

<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp" />
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp" />
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Quản lý danh mục</h1>
                <div class="mt-5">
                    <div class="row">
                        <div class="col-12 mx-auto">
                            <div class="d-flex justify-content-between">
                                <h3>Bảng danh mục</h3>
                                <form:form action="/admin/category" method="post" modelAttribute="newCategory">
                                    <div class="d-flex">
                                        <form:input path="name" class="form-control" placeholder="Tên danh mục"/>
                                        <button class="btn btn-outline-secondary ms-1">Tìm</button>
                                    </div>
                                </form:form>


                                <a href="/admin/category/create" class="btn btn-primary btncreate"> <i class="fas fa-plus-circle me-1"></i>Thêm danh mục</a>
                            </div>
                            <hr />
                            <table class=" table table-bordered table-hover table-striped">
                                <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Tên</th>
                                    <th>Hành động</th>

                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="cate" items="${cates}">

                                    <tr>
                                        <th>${cate.categoryId}</th>

                                        <td>${cate.name}</td>

                                        <td class="d-flex">
                                            <a href="/admin/category/update/${cate.categoryId}" class="btn btn-warning ms-1 me-1 text-light" >
                                                <i class="fas fa-edit"></i> Cập nhật
                                            </a>
                                            <a href="/admin/category/delete/${cate.categoryId}" class="btn btn-danger">
                                                <i class="fas fa-trash"></i> Xóa
                                            </a>
                                        </td>
                                    </tr>

                                </c:forEach>

                                </tbody>
                            </table>

                                <nav aria-label="Page navigation example">
                                    <ul class="pagination justify-content-center">
                                        <li class="page-item">
                                            <a class="${1 eq currentPage ? 'disabled page-link' : 'page-link'}"
                                               href="/admin/category?page=${currentPage - 1}"
                                               aria-label="Previous">
                                                <span aria-hidden="true">&laquo;</span>
                                            </a>
                                        </li>
                                        <c:forEach begin="0" end="${totalPages - 1}" varStatus="loop">
                                            <li class="page-item">
                                                <a class="${(loop.index + 1) eq currentPage ? 'active page-link' : 'page-link'}"
                                                   href="/admin/category?page=${loop.index + 1}">
                                                        ${loop.index + 1}
                                                </a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item">
                                            <a class="${totalPages eq currentPage ? 'disabled page-link' : 'page-link'}"
                                               href="/admin/category?page=${currentPage + 1}" aria-label="Next">
                                                <span aria-hidden="true">&raquo;</span>
                                            </a>
                                        </li>
                                    </ul>
                                </nav>


                        </div>

                    </div>

                </div>
            </div>
        </main>
        <jsp:include page="../layout/footer.jsp" />
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<script src="/js/scripts.js"></script>

</body>

</html>