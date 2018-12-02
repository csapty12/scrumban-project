import React, { Component } from "react";
import ProjectForm from "./Forms/ProjectForm";

class AddProject extends Component {
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
    const { errors } = this.state;
    return <ProjectForm type="Create" errors={errors} />;
  }
}
export default AddProject;
