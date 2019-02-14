import React from 'react';
import style from './Modal.css';
const ModalDialog = props => {
  let displayBlock = 'none';
  if (props.toggleDialog) {
    displayBlock = 'block';
  }

  return (
    <div id="myModal" className={style.modal} style={{ display: displayBlock }}>
      <div className={style.modalContent}>
        <div className={style.modalHeader}>
          {
            <span className={style.close} onClick={props.handleCloseDialog}>
              &times;
            </span>
          }
          <h2>Modal Header</h2>
        </div>
        <div className={style.modalBody}>
          <p>Some text in the Modal Body</p>
          <p>Some other text...</p>
        </div>
        <div className="modal-footer">
          <h3>Modal Footer</h3>
        </div>
      </div>
    </div>
  );
};

export default ModalDialog;
