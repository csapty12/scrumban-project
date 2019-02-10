import React from 'react';

export default ({
  name,
  date,
  toggleMenu,
  isMenuOpen,
  handleUpdate,
  handleDelete,
}) => (
  <div>
    <h2>{name}</h2>
    <span data-test="tile__date">{date.toDateString()}</span>
    <button onClick={toggleMenu} />
    {isMenuOpen && (
      <ol>
        <li data-test="tile__update" onClick={handleUpdate} />
        <li data-test="tile__delete" onClick={handleDelete} />
      </ol>
    )}
  </div>
);
