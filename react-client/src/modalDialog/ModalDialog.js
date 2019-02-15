import React from 'react';
import style from './Modal.css';

const ModalDialog = ({ toggleDialog, handleCloseDialog, type, onKeyDown }) => {
  let displayFlex = 'none';
  if (toggleDialog) {
    displayFlex = 'flex';
  }

  return (
    <div className={style.overlay}>
      <div
        id="myModal"
        className={style.modal}
        style={{ display: displayFlex }}
        role="dialog"
      >
        <div className={style.modalContent} >
          <div className={style.modalHeader} >
            <span
              className={style.close}
              onClick={handleCloseDialog}
              role="presentation"
              
            >
              &times;
            </span>

            <h2>{type} Project</h2>
          </div>
          <div className={style.modalBody}>
            <form action="#">
              <div className={style.row}>
                <div className={style.col25}>
                  <label htmlFor="pname" name="projectName">Project Name</label>
                </div>
                <div className={style.col75}>
                  <input type="text" id="projectName" name="projectName" />
                </div>
              </div>
              <div className="row">
                <div className={style.col25}>
                  <label htmlFor="subject">Description</label>
                </div>
                <div className={style.col75}>
                  <textarea
                    id="description"
                    name="description"
                    placeholder="This problem aims to solve..."
                    style={{ height: '200px' }}
                  />
                </div>
              </div>
              <div className={style.row}>
                <button type="submit" className={style.projectButton} data-test="button__create">{type}</button>
                <button onClick={handleCloseDialog} className={style.projectButton}  data-test="button__cancel">Cancel</button>
              </div>
            </form>
            <br />
          </div>
        </div>
      </div>
    </div>
  );
};

export default ModalDialog;
