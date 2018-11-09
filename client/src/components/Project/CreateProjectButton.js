import React, { Fragment } from "react";
import { Link } from "react-router-dom";

const CreateProjectButton = () => {
  return (
    <Fragment>
      {/*react fragment is a react block which means we dont have to use another div */}
      <Link to="/addProject" className="btn btn-lg btn-info">
        Create a Project <i className="fa fa-plus-circle" />
      </Link>
    </Fragment>
  );
};

export default CreateProjectButton;
