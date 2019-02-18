import React from 'react';
import style from './navbar.css';

export default () => (
  <nav className={style.navbar}>
    <span className={style.navbarToggle} id="js-navbar-toggle">
      <i className="fas fa-bars" />
    </span>
    <a href="#" className={style.logo}>
      logo
    </a>
    <ul className={style.mainNav} id="js-menu">
      <li>
        <a href="#" className={style.navLinks}>
          Login
        </a>
      </li>
      <li>
        <a href="#" className={style.navLinks}>
          Register
        </a>
      </li>
    </ul>
  </nav>
);
