import React from "react";
import classnames from "classnames";

const TextArea = props => {
  return (
    <div className="form-group">
      <textarea
        className={classnames("form-control form-control-sm", {
          "is-invalid": props.onError
        })}
        placeholder={props.placeholder}
        name={props.name}
        value={props.description}
        onChange={props.handleChange}
      />
      {props.onError && <div className="invalid-feedback">{props.onError}</div>}
    </div>
  );
};
export default TextArea;
