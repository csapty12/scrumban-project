import React, { Component } from "react";
import { withStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import IconButton from "@material-ui/core/IconButton";
import MoreVertIcon from "@material-ui/icons/MoreVert";
import Button from "@material-ui/core/Button";
import CardActions from "@material-ui/core/CardActions";
const options = ["Update Info", "Delete Project"];

const styles = theme => ({});
class ProjectItem extends Component {
  render() {
    console.log("this.props: " + JSON.stringify(this.props));
    return (
      <Card>
        <CardHeader
          action={
            <IconButton>
              <MoreVertIcon />
            </IconButton>
          }
          title={this.props.project.projectName}
          subheader={"created: " + this.props.project.startDate}
        />
        <CardActions>
          <Button size="small" color="primary">
            Dashboard
          </Button>
        </CardActions>
      </Card>
    );
  }
}
export default withStyles(styles)(ProjectItem);
