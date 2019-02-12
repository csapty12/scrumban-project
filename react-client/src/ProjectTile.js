import React from 'react';
import style from './projectTile.css';

export default ({
  name,
  date,
  toggleMenu,
  isMenuOpen,
  handleUpdate,
  handleDelete,
}) => (
  <div className={style.card}>
    <h2 className={style.projectTitle}>{name}</h2>
    <div onClick={toggleMenu} className={style.dropDown}>
      <button className={style.dropDownBtn}>...</button>
      {isMenuOpen && (
        <div className={style.dropDownContent}>
          <a data-test="tile__update" onClick={handleUpdate}>
            <div>update</div>
          </a>
          <a data-test="tile__delete" onClick={handleDelete}>
            <div>delete</div>
          </a>
        </div>
      )}
    </div>
    <div className={style.createdAt}>
      <span data-test="tile__date">created: {"'14-12-2019'"}</span>
    </div>
    <footer className={style.dashboardLink}>Dashboard</footer>
  </div>
);
