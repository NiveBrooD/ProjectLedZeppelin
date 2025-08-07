<%--
  Created by IntelliJ IDEA.
  User: ramis
  Date: 7/27/2025
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@include file="parts/header.jsp" %>
<head>
    <style>
        .signupFrm {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 90%;
            max-width: 400px;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }

        .title {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }

        .inputContainer {
            position: relative;
            margin-bottom: 25px;
        }

        .input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .roleLabel {
            display: block;
            margin-bottom: 10px;
            color: #666;
            font-size: 14px;
        }

        .roleOptions {
            display: flex;
            gap: 20px;
            margin-top: 5px;
        }

        .radioLabel {
            display: flex;
            align-items: center;
            gap: 8px;
            cursor: pointer;
            color: #333;
        }

        .radioLabel input[type="radio"] {
            margin: 0;
            width: 16px;
            height: 16px;
            cursor: pointer;
        }

        .radioLabel span {
            font-size: 14px;
            color: #333;
            opacity: 1;
        }
    </style>
</head>

<body>
<div class="signupFrm">
    <form method="post" action="signup" class="form">
        <h1 class="title">Sign up</h1>
        <div class="inputContainer">
            <input type="text" class="input"  name="login" placeholder="Login" required>
            <label style="color: #666" for="" class="label">Login</label>
        </div>

        <div class="inputContainer">
            <input type="password" class="input"  name="password" placeholder="Password" required>
            <label style="color: #666" for="" class="label">Password</label>
        </div>

        <div class="inputContainer">
            <label class="roleLabel">Role</label>
            <div class="roleOptions">
                <label class="radioLabel">
                    <input type="radio" name="role" value="USER" checked>
                    <span>User</span>
                </label>
                <label class="radioLabel">
                    <input type="radio" name="role" value="GUEST">
                    <span>Guest</span>
                </label>
                <label class="radioLabel">
                    <input type="radio" name="role" value="ADMIN">
                    <span>Admin</span>
                </label>
            </div>
        </div>
        <input type="submit" class="submitBtn" value="Sign up">
    </form>
</div>
</body>
</html>
