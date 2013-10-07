<%-- 
    Document   : layout
    Created on : Sep 15, 2013, 10:58:00 AM
    Author     : sameerkvaidya
--%>

<%@tag description="simple layout" pageEncoding="UTF-8"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Couchbase Sample Project</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="The Couchbase Java Beer-Sample App">
    <meta name="author" content="Couchbase, Inc. 2012">


    <link type="text/css" href="/env/style/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" href="/env/style/beersample.css" rel="stylesheet">
    <link type="text/css" href="/env/style/bootstrap-responsive.min.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
  </head>
  <body>
    <div class="container-narrow">
      <div class="masthead">
        <ul class="nav nav-pills pull-right">
          <li><a href="/env/serv/all">Home</a></li>
          <li><a href="/env/serv/add">Add New</a></li>
          
        </ul>
        <h2 class="muted">Couchbase Sample</h2>
      </div>
      <hr>
      <div class="row-fluid">
        <div class="span12">
            <jsp:doBody/>
        </div>
      </div>
      <hr>
      <div class="footer">
        <p>&copy; usac.org</p>
      </div>
    </div>
        <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    
  </body>
</html>