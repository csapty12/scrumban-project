import React, { Component } from "react";
import ProjectForm from "../Forms/ProjectForm";

class UpdateProject extends Component {
  constructor(props) {
    super(props);
    this.state = {
      projectName: "",
      projectIdentifier: "",
      description: "",
      startDate: "",
      endDate: "",
      errors: {}
    };
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  render() {
    return (
      <ProjectForm
        type="Edit"
        projectIdentifier={this.props.match.params.projectIdentifier}
        disabledId={true}
      />
    );
  }
}

export default UpdateProject;
