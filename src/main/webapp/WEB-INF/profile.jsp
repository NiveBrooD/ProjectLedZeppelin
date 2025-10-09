<%--
  Created by IntelliJ IDEA.
  User: ramis
  Date: 7/27/2025
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@include file="parts/header.jsp" %>
<%@page import="com.javarush.ramis.dto.Role" %>
<body>
<main class="container my-5">
    <form method="post" action="profile"
          style="max-width: 600px; margin: 0 auto; padding: 2rem;
                 background: white; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">

        <h1 style="color: #0f2537; margin-bottom: 1.5rem; text-align: center; font-weight: 600;">Edit Profile</h1>

        <div class="mb-3" style="margin-bottom: 1.5rem;">
            <label class="form-label" style="color: #0f2537; font-weight: 500;">Login</label>
            <input type="text" class="form-control" name="login"
                   value="${sessionScope.user.login}" required
                   style="border: 1px solid #ddd; border-radius: 4px; padding: 0.75rem;">
        </div>

        <div class="mb-3" style="margin-bottom: 1.5rem;">
            <label class="form-label" style="color: #0f2537; font-weight: 500;">Password</label>
            <input type="password" class="form-control" name="password" required
                   style="border: 1px solid #ddd; border-radius: 4px; padding: 0.75rem;">
        </div>

        <div class="mb-4" style="margin-bottom: 2rem;">
            <label class="form-label" style="color: #0f2537; font-weight: 500;">Role</label>
            <c:choose>
                <c:when test="${sessionScope.user.role eq Role.ADMIN}">
                    <div class="d-flex gap-3 mt-2">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="role" value="USER"
                                   style="border-color: #0f2537;">
                            <label class="form-check-label" style="color: #212529">User</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="role" value="GUEST"
                                   style="border-color: #0f2537">
                            <label class="form-check-label" style="color: #212529">Guest</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="role" value="ADMIN"
                                   style="border-color: #0f2537" checked>
                            <label class="form-check-label" style="color: #212529">Admin</label>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <input type="text" class="form-control" name="role" value="${sessionScope.user.role}" readonly
                           style="background-color: #f8f9fa; border: 1px solid #ddd; padding: 0.75rem;">
                </c:otherwise>
            </c:choose>
        </div>

        <button type="submit"
                style="width: 100%; padding: 0.75rem; background-color: #0f2537; color: white;
                       border: none; border-radius: 4px; font-weight: 500; transition: all 0.3s;">
            Save Changes
        </button>
    </form>
    <form method="post" action="delete" style="margin-top: 1rem;">
        <button type="submit"
                style="width: 100%; padding: 0.75rem; background-color: #dc3545; color: white;
                   border: none; border-radius: 4px; font-weight: 500;">
            Delete profile
        </button>
    </form>
</main>
<script>
    document.querySelector('form[action="delete"]').addEventListener('submit', function(e) {
        if (!confirm('Are you sure you want to delete your profile?')) {
            e.preventDefault();
        }
    });
</script>
</body>
</html>
