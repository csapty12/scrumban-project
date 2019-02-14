import React from 'react';
import style from './Modal.css';

const ModalDialog = ({ toggleDialog, handleCloseDialog, type }) => {
  let displayBlock = 'none';
  if (toggleDialog) {
    displayBlock = 'flex';
  }

  return (
    <div className={style.overlay}>
      <div
        id="myModal"
        className={style.modal}
        style={{ display: displayBlock }}
        role="dialog"
      >
        <div className={style.modalContent}>
          <div className={style.modalHeader}>
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
            <form action="/action_page.php">
              <div className={style.row}>
                <div className={style.col25}>
                  <label htmlFor="pname">Project Name</label>
                </div>
                <div className={style.col75}>
                  <input type="text" id="pname" name="projectName" />
                </div>
              </div>
              <div className="row">
                <div className={style.col25}>
                  <label htmlFor="subject">Description</label>
                </div>
                <div className={style.col75}>
                  <textarea
                    id="subject"
                    name="subject"
                    placeholder="Write something.."
                    style={{ height: '200px' }}
                  />
                </div>
              </div>
              <div className={style.row}>
                <input type="submit" value="Create" />
                <input type="submit" value="Cancel" />
              </div>
              {
                // <div className={style.row}>
                //   <div className={style.modalFooter}>
                //     <h3>Cancel </h3>
                //     <h3>{type}</h3>
                //   </div>
                //   {/*<input type="submit" value="Submit" />*/}
                // </div>
              }
            </form>
            <br />
          </div>
        </div>
      </div>
    </div>
  );
};

export default ModalDialog;
