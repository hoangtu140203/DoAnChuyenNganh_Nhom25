<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
        $(document).ready(() => {
            const avatarFile = $("#avatarFile");
            const orgImage = "${newProduct.image}";
            if (orgImage) {
                const urlImage = "/images/product/" + orgImage;
                $("#avatarPreview").attr("src", urlImage);
                $("#avatarPreview").css({ "display": "block" });
            }

            avatarFile.change(function (e) {
                const imgURL = URL.createObjectURL(e.target.files[0]);
                $("#avatarPreview").attr("src", imgURL);
                $("#avatarPreview").css({ "display": "block" });
            });
        });
    </script>
</head>
<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp" />
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp" />
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Products</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                    <li class="breadcrumb-item"><a href="/admin/product">Product</a></li>
                    <li class="breadcrumb-item active">Update</li>
                </ol>
                <div class=" mt-5">
                    <div class="row">
                        <div class="col-md-6 col-12 mx-auto">
                            <h3>Update a product</h3>
                            <hr />
                            <form:form class="form" action="/admin/product/update" method="post" modelAttribute="newProduct" enctype="multipart/form-data">
                                <!-- Các trường nhập thông tin sản phẩm -->
                                <div class="form-group">
                                    <label for="name">Tên sản phẩm</label>
                                    <form:input id="name" path="name" class="form-control" placeholder="Tên sản phẩm" />
                                </div>

                                <div class="form-group">
                                    <label for="price">Giá</label>
                                    <form:input id="price" path="price" class="form-control" placeholder="Giá sản phẩm" />
                                </div>

                                <div class="form-group">
                                    <label for="quantity">Số lượng</label>
                                    <form:input id="quantity" path="quantity" class="form-control" placeholder="Số lượng" />
                                </div>

                                <div class="form-group">
                                    <label for="discount">Giảm giá</label>
                                    <form:input id="discount" path="discount" class="form-control" placeholder="Giảm giá" />
                                </div>

                                <div class="form-group">
                                    <label for="description">Mô tả</label>
                                    <form:textarea id="description" path="description" class="form-control" placeholder="Mô tả sản phẩm"></form:textarea>
                                </div>


                                <div class="mb-3 col-12 col-md-6">
                                    <label for="avatarFile" class="form-label">Image:</label>
                                    <input class="form-control" type="file" id="avatarFile"
                                           accept=".png, .jpg, .jpeg" name="hoidanitFile" />
                                </div>

                                <button type="submit" class="btn btn-primary">Cập nhật sản phẩm</button>
                            </form:form>


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