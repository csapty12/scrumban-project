import React, { Component, Fragment } from "react";
import { withStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import IconButton from "@material-ui/core/IconButton";
import MoreVertIcon from "@material-ui/icons/MoreVert";
import Button from "@material-ui/core/Button";
import CardActions from "@material-ui/core/CardActions";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
const options = ["Update Info", "Delete Project"];

const styles = theme => ({});

class ProjectItem extends Component {
  constructor(props) {
    super(props);
    this.state = {
      anchorEl: null
    };
  }

  handleClick = event => {
    this.setState({ anchorEl: event.currentTarget });
  };

  handleClose = option => {
    this.setState({ anchorEl: null });
  };

  render() {
    const { classes, project } = this.props;
    const { anchorEl } = this.state;
    const open = Boolean(anchorEl);
    return (
      <div className="col-md-6 col-lg-4 item">
        <Card>
          <CardHeader
            action={
              <Fragment>
                <IconButton
                  aria-label="More"
                  aria-owns={open ? "long-menu" : undefined}
                  aria-haspopup="true"
                  onClick={this.handleClick}
                >
                  <MoreVertIcon />
                </IconButton>
                <Menu
                  id="long-menu"
                  anchorEl={anchorEl}
                  open={open}
                  onClose={this.handleClose}
                >
                  <MenuItem key="deleteItem">Delete Project</MenuItem>
                </Menu>
              </Fragment>
            }
            title={project.projectName}
            subheader={"created: " + project.startDate}
          />
          <CardActions>
            <Button size="small" color="primary">
              Dashboard
            </Button>
          </CardActions>
        </Card>
      </div>
    );
  }
}
export default withStyles(styles)(ProjectItem);
