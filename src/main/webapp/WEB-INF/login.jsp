<%--
  Created by IntelliJ IDEA.
  User: ramis
  Date: 7/25/2025
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@include file="parts/header.jsp" %>
<body>
<section class="position-relative py-4 py-xl-5">
    <div class="container">
        <div class="row mb-5">
            <div class="col-md-8 col-xl-6 text-center mx-auto">
                <h2 style="color: white">Log in</h2>
            </div>
        </div>
        <div class="row d-flex justify-content-center">
            <div class="col-md-6 col-xl-3">
                <div class="card mb-5">
                    <div class="card-body d-flex flex-column align-items-center">
                        <form class="text-center" method="post" action="login">
                            <div class="mb-3">
                                <input class="form-control" type="text" name="login" placeholder="Login" required>
                            </div>
                            <div class="mb-3">
                                <input class="form-control" type="password" name="password" placeholder="Password"
                                       required>
                            </div>
                            <div class="mb-3">
                                <button class="btn btn-primary w-100 d-block" type="submit"
                                        style="background-color: #0f2537; border-color: #0f2537;">
                                    Login
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
