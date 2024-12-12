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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/apexcharts/4.1.0/apexcharts.min.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet" />
</head>

<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp" />
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp" />
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">THỐNG KÊ</h1>
                <ol class="breadcrumb mb-4">

                </ol>
                <div class="row">
<%--                    <div class="col-xl-3 col-md-6 card2">--%>
<%--                        <div class="card2-inner2">--%>
<%--                            <p class="text-primary2">PRODUCTS</p>--%>
<%--                            <span class="material-icons-outlined2 text-blue2">inventory_2</span>--%>
<%--                        </div>--%>
<%--                        <span class="text-primary2 font-weight-bold2">249</span>--%>
<%--                    </div>--%>

<%--                    <div class="col-xl-3 col-md-6 card2">--%>
<%--                        <div class="card2-inner2">--%>
<%--                            <p class="text-primary2">PURCHASE ORDERS</p>--%>
<%--                            <span class="material-icons-outlined2 text-orange2">add_shopping_cart</span>--%>
<%--                        </div>--%>
<%--                        <span class="text-primary2 font-weight-bold2">83</span>--%>
<%--                    </div>--%>

<%--                    <div class="col-xl-3 col-md-6 card2">--%>
<%--                        <div class="card2-inner2">--%>
<%--                            <p class="text-primary2">SALES ORDERS</p>--%>
<%--                            <span class="material-icons-outlined2 text-green2">shopping_cart</span>--%>
<%--                        </div>--%>
<%--                        <span class="text-primary2 font-weight-bold2">79</span>--%>
<%--                    </div>--%>

<%--                    <div class="col-xl-3 col-md-6 card2">--%>
<%--                        <div class="card2-inner2">--%>
<%--                            <p class="text-primary2">INVENTORY ALERTS</p>--%>
<%--                            <span class="material-icons-outlined2 text-red2">notification_important</span>--%>
<%--                        </div>--%>
<%--                        <span class="text-primary font-weight-bold2">56</span>--%>
<%--                    </div>--%>

                    <div class="col-xl-3 col-md-6">
                        <div class="card mb-4">
                            <div class="card-body font-weight-bold2 d-flex justify-content-between align-items-center">
                                <div>
                                    <div class="text-uppercase">Doanh số toàn thời gian</div>
                                    <div class="font-weight-bold">${totalRevenue}</div>
                                </div>
                                <span class="material-symbols-outlined text-success icon-large">payments</span>
                            </div>
                            <div class="card-footer d-flex align-items-center justify-content-between bg-success">
                                <a class="small text-white stretched-link" href="#">Chi tiết</a>
                                <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-3 col-md-6">
                        <div class="card mb-4">
                            <div class="card-body font-weight-bold2 d-flex justify-content-between align-items-center">
                                <div>
                                    <div class="text-uppercase">Tổng tất cả khách hàng</div>
                                    <div class="font-weight-bold">${totalCustomer}</div>
                                </div>
                                <span class="material-symbols-outlined text-primary icon-large">group</span>
                            </div>
                            <div class="card-footer d-flex align-items-center justify-content-between bg-primary">
                                <a class="small text-white stretched-link" href="#">Chi tiết</a>
                                <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-3 col-md-6">
                        <div class="card mb-4">
                            <div class="card-body font-weight-bold2 d-flex justify-content-between align-items-center">
                                <div>
                                    <div class="text-uppercase">Tổng tất cả hóa đơn</div>
                                    <div class="font-weight-bold">${totalBill}</div>
                                </div>
                                <span class="material-symbols-outlined text-warning icon-large">receipt_long</span>
                            </div>
                            <div class="card-footer d-flex align-items-center justify-content-between bg-warning">
                                <a class="small text-white stretched-link" href="#">Chi tiết</a>
                                <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-3 col-md-6">
                        <div class="card mb-4">
                            <div class="card-body font-weight-bold2 d-flex justify-content-between align-items-center">
                                <div>
                                    <div class="text-uppercase">Tổng số sản phẩm</div>
                                    <div class="font-weight-bold">${totalProduct}</div>
                                </div>
                                <span class="material-symbols-outlined text-danger icon-large">inventory_2</span>
                            </div>
                            <div class="card-footer d-flex align-items-center justify-content-between bg-danger">
                                <a class="small text-white stretched-link" href="#">Chi tiết</a>
                                <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xl-6">
                        <div class="card mb-4">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <div>
                                    <i class="fas fa-chart-area me-1"></i>
                                    Thống kê doanh thu
                                </div>
                                <div class="d-flex gap-2">
                                    <button class="btn btn-success" onclick="fetchRevenue('${date}');">Ngày</button>
                                    <button class="btn btn-warning" onclick="fetchRevenue(null, '2024');">Tháng</button>
                                </div>
                            </div>
                            <div class="card-body">
                                <div id="chart-area"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-6">
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-chart-bar me-1"></i>
                                Thị phần các hãng
                            </div>
                            <div class="card-body">
                                <div id="pie-chart"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fas fa-table me-1"></i>
                        Top sản phẩm
                    </div>
                    <div class="card-body">
                        <table id="datatablesSimple">
                            <thead>
                            <tr>
                                <th>Mã sản phẩm</th>
                                <th>Tên</th>
                                <th>Nhà sản xuất</th>
                                <th>Giá</th>
                                <th>Doanh số</th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
                <div>
                    <h1>Export PDF</h1>
                    <button onclick="downloadPdf()">Download PDF</button>
                </div>
            </div>
        </main>
        <jsp:include page="../layout/footer.jsp" />
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<script src="/js/scripts.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
<%--<script src="/js/datatables-simple-demo.js"></script>--%>


<%--<script>--%>
<%--    const chartData = {--%>
<%--        xAxis: [],--%>
<%--        yAxis: []--%>
<%--    };--%>
<%--    <c:forEach var="revenue" items="${dailyRevenue}">--%>
<%--    chartData.xAxis.push("${revenue.day}");--%>
<%--    console.log(${revenue.dailyRevenue});--%>
<%--    chartData.yAxis.push(${revenue.dailyRevenue});--%>
<%--    </c:forEach>;--%>

<%--    // Pass chartData to ApexCharts--%>
<%--    var options = {--%>
<%--        series: [{--%>
<%--            name: 'Daily Revenue',--%>
<%--            data: chartData.yAxis--%>
<%--        }],--%>
<%--        chart: {--%>
<%--            type: 'line',--%>
<%--            height: 350--%>
<%--        },--%>
<%--        xaxis: {--%>
<%--            categories: chartData.xAxis--%>
<%--        }--%>
<%--    };--%>

<%--    var chart = new ApexCharts(document.querySelector("#chart"), options);--%>
<%--    chart.render();--%>
<%--</script>--%>

<script>
    const chartData = {
        xAxis: [],
        yAxis: []
    };

    // Function to load data with a dynamic startDate
    async function fetchRevenue(startDate, year) {
        let url = '/revenue';
        if (startDate) {
            url += `?startDate=`+startDate;
        } else if (year) {
            url += `?year=`+year;
        } else {
            throw new Error('Either startDate or year must be provided');
        }

        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const datarevenue = await response.json();
            console.log(datarevenue); // Debugging: Log the fetched data

            // Populate chartData with the fetched data
            chartData.xAxis = datarevenue.map(revenue => revenue.label);
            chartData.yAxis = datarevenue.map(revenue => revenue.totalRevenue);

            // Configure and render the ApexCharts chart
            // const options = {
            //     series: [{
            //         name: 'Revenue',
            //         data: chartData.yAxis
            //     }],
            //     chart: {
            //         type: 'line',
            //         height: 350
            //     },
            //     xaxis: {
            //         categories: chartData.xAxis
            //     }
            // };
            //
            // const chartElement = document.querySelector("#chart");
            // if (chartElement) {
            //     const chart = new ApexCharts(chartElement, options);
            //     chart.render();
            // }
            var options1 = {
                chart: {
                    id: "chart2",
                    type: "area",
                    height: 350,
                    foreColor: "#000000",
                    toolbar: {
                        autoSelected: "pan",
                        show: false
                    }
                },
                colors: ["#00BAEC"],
                stroke: {
                    width: 3
                },
                grid: {
                    borderColor: "#555",
                    clipMarkers: false,
                    yaxis: {
                        lines: {
                            show: false
                        }
                    }
                },
                dataLabels: {
                    enabled: false
                },
                fill: {
                    gradient: {
                        enabled: true,
                        opacityFrom: 0.55,
                        opacityTo: 0
                    }
                },
                markers: {
                    size: 5,
                    colors: ["#000524"],
                    strokeColor: "#00BAEC",
                    strokeWidth: 3
                },
                series: [
                    {
                        data: chartData.yAxis
                    }
                ],
                tooltip: {
                    theme: "dark"
                },
                xaxis: {
                    type: "datetime",
                    categories: chartData.xAxis
                },
                yaxis: {
                    min: 0,
                    tickAmount: 4
                }
            };

            var chart1 = new ApexCharts(document.querySelector("#chart-area"), options1);

            chart1.render();
        } catch (error) {
            console.error("Error loading revenue data:", error);
        } finally {
        // Reset the URL
            url = '/revenue';
        }
    }

    // Load default data on page load
    document.addEventListener("DOMContentLoaded", () => {
        fetchRevenue('${date}'); // Call the function with the default start date
    });
</script>



<script>
    // Parse data passed from the server
    const pieChartDataC = {
        series: [],
        labels: []
    };

    // Format data for ApexCharts
    <c:forEach var="datapie" items="${pieChartData}">
    console.log(${datapie.marketShare});
    pieChartDataC.series.push(${datapie.marketShare});
    console.log("${datapie.name}");
    pieChartDataC.labels.push("${datapie.name}");
    </c:forEach>;

    // Render the pie chart
    const options2 = {
        chart: {
            type: 'pie',
            height: 358
        },
        series: pieChartDataC.series,
        labels: pieChartDataC.labels,
    };

    const chart2 = new ApexCharts(document.querySelector("#pie-chart"), options2);
    chart2.render();
</script>
<%--<script>--%>
<%--    async function loadData(orderBy) {--%>
<%--        const response = await fetch(`/top-products?orderBy=`+orderBy);--%>
<%--        const data = await response.json();--%>
<%--        console.log(data);--%>
<%--        const products = data;--%>
<%--        const tableBody = document.querySelector("#datatablesSimple tbody");--%>
<%--        tableBody.innerHTML = ""; // Clear existing rows--%>

<%--        products.forEach(product => {--%>
<%--            const row = tableBody.insertRow(); // Create a new row--%>

<%--            // Add cells to the row--%>
<%--            const cellId = row.insertCell(0);--%>
<%--            const cellName = row.insertCell(1);--%>
<%--            const cellCategory = row.insertCell(2);--%>
<%--            const cellPrice = row.insertCell(3);--%>
<%--            const cellSales = row.insertCell(4);--%>

<%--            // Fill the cells with data--%>
<%--            cellId.textContent = product.productId;--%>
<%--            cellName.textContent = product.name;--%>
<%--            cellCategory.textContent = product.categoryName;--%>
<%--            cellPrice.textContent = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(product.price);--%>
<%--            cellSales.textContent = product.totalSales;--%>
<%--        });--%>
<%--    }--%>

<%--    // Load default data on page load--%>
<%--    loadData('ASC');--%>

<%--</script>--%>
<script>
    let dataTable; // Global variable to hold the DataTable instance

    async function loadData(orderBy) {
        try {
            const response = await fetch(`/top-products?orderBy=` + orderBy);
            const data = await response.json();

            console.log(data);
            const products = data;

            const tableBody = document.querySelector("#datatablesSimple tbody");
            tableBody.innerHTML = ""; // Clear existing rows

            // Populate the table
            products.forEach(product => {
                const row = tableBody.insertRow(); // Create a new row

                // Add cells to the row
                const cellId = row.insertCell(0);
                const cellName = row.insertCell(1);
                const cellCategory = row.insertCell(2);
                const cellPrice = row.insertCell(3);
                const cellSales = row.insertCell(4);

                // Fill the cells with data
                cellId.textContent = product.productId;
                cellName.textContent = product.name;
                cellCategory.textContent = product.categoryName;
                cellPrice.textContent = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(product.price);
                cellSales.textContent = product.totalSales;
            });

            // Initialize or refresh Simple-DataTables
            if (dataTable) {
                // If already initialized, refresh it
                dataTable.refresh();
            } else {
                // Initialize the DataTable
                dataTable = new simpleDatatables.DataTable("#datatablesSimple");
            }
        } catch (error) {
            console.error("Error loading data:", error);
        }
    }

    // Load default data on page load
    document.addEventListener("DOMContentLoaded", () => {
        loadData("DESC");
    });
</script>

<script>
    async function downloadPdf() {
        try {
            const response = await fetch('/admin/export', {
                method: 'GET',
                headers: {
                    'Accept': 'application/pdf'
                }
            });

            if (!response.ok) {
                throw new Error('Failed to download PDF');
            }

            // Convert the response to a Blob
            const blob = await response.blob();

            // Create a temporary URL for the Blob
            const url = window.URL.createObjectURL(blob);

            // Create a temporary link element
            const a = document.createElement('a');
            a.href = url;
            a.download = 'products.pdf'; // Set the filename
            document.body.appendChild(a);
            a.click(); // Trigger the download
            a.remove(); // Remove the link from the DOM

            // Revoke the temporary URL
            window.URL.revokeObjectURL(url);
        } catch (error) {
            console.error('Error downloading the PDF:', error);
            alert('Failed to download the PDF.');
        }
    }
</script>

</body>

</html>