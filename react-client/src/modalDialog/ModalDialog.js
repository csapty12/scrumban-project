import React from 'react';
import style from './Modal.css';

const ModalDialog = ({
  toggleDialog,
  handleCloseDialog,
  handleEscKeyPress,
}) => {
  let displayBlock = 'none';
  if (toggleDialog) {
    displayBlock = 'block';
  }

  return (
    <div
      id="myModal"
      className={style.modal}
      style={{ display: displayBlock }}
      role="dialog"
    >
      <div className={style.modalContent}>
        <div className={style.modalHeader}>
          {
            <span
              className={style.close}
              onClick={handleCloseDialog}
              role="presentation"
            >
              &times;
            </span>
          }
          <h2>Modal Header</h2>
        </div>
        <div className={style.modalBody}>
          <p>Some text in the Modal Body</p>
          <p>Some other text...</p>
        </div>
        <div className={style.modalFooter}>
          <h3>Modal Footer</h3>
        </div>
      </div>
    </div>
  );
};

export default ModalDialog;
