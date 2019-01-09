import React from "react";
import classnames from "classnames";
const TextInput = props => {
  return (
    <div className="form-group">
      <input
        type={props.type}
        id={props.name}
        className={classnames("form-control form-control-sm", {
          "is-invalid": props.onError
        })}
        placeholder={props.placeholder}
        name={props.name}
        value={props.value}
        onChange={props.handleChange}
      />
      {props.onError && <div className="invalid-feedback">{props.onError}</div>}
    </div>
  );
};
export default TextInput;
