<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Google Chart Column Chart Example</title>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        $.getJSON('/api/products/ratings', function(data) {
            var chartData = new google.visualization.DataTable();
            chartData.addColumn('string', 'Product');
            chartData.addColumn('number', 'Stars');
            chartData.addColumn('number', 'Reviews');
            
            $.each(data, function(key, val) {
                chartData.addRow([val.product, val.stars, val.reviews]);
            });
            
            var options = {
                title: 'Product Ratings',
                hAxis: {title: 'Product',  titleTextStyle: {color: '#333'}},
                vAxis: {minValue: 0},
            };
            
            var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
            chart.draw(chartData, options);
        });
    }

        }
    </script>
</head>
<body>
    <div id="chart_div" style="width: 900px; height: 500px;"></div>
</body>
</html>
