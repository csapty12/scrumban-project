import React from 'react';
import style from "./navbar.css";

export default()=>(
    <div className={style.topNav}>
        <a>Home</a>
        <div className={style.topNavRight}>
            <a>Login</a>
            <a>Register</a>
        </div> 
    </div>
)