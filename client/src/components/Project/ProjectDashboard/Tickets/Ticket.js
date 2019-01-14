import React, { Component, Fragment } from "react";
import { Link } from "react-router-dom";
import "../projectDashboard.css";
import { Draggable } from "react-beautiful-dnd";
import styled from "styled-components";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import IconButton from "@material-ui/core/IconButton";
import MoreVertIcon from "@material-ui/icons/MoreVert";
import Button from "@material-ui/core/Button";
import CardActions from "@material-ui/core/CardActions";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContentText from "@material-ui/core/DialogContentText";
import TextField from "@material-ui/core/TextField";
import { withStyles } from "@material-ui/core/styles";
import classNames from "classnames";

const TicketContainer = styled.div``;

const styles = theme => ({
  cards: {
    marginTop: 5,
    marginBottom: 5,
    borderRadius: 1
  },
  high: {
    borderLeft: "3px solid red"
  },
  medium: {
    borderLeft: "3px solid #FF8C19"
  },
  low: {
    borderLeft: "3px solid #42CC00"
  }
});

class Ticket extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isHovering: false
    };
  }
  handleDelete = ticket => {
    console.log("ticket: " + JSON.stringify(ticket));
    console.log("props: " + JSON.stringify(this.props));
    this.props.deleteTicket(ticket);
  };
  handleMouseHover = () => {
    this.setState(this.toggleHoverState);
  };
  toggleHoverState = state => {
    return {
      isHovering: !state.isHovering
    };
  };
  render() {
    const { ticket, classes } = this.props;
    console.log("json classes: " + JSON.stringify(classes));
    const priority = ticket.priority;
    console.log("priort:  " + priority);
    let priorityClass;
    if (priority === "low") {
      priorityClass = "low";
    }
    if (priority === "medium") {
      priorityClass = "medium";
    }
    if (priority === "high") {
      priorityClass = "high";
    }
    return (
      <TicketContainer>
        <Card
          className={classNames(
            classes.cards,
            { [classes.high]: priorityClass === "high" },
            { [classes.medium]: priorityClass === "medium" },
            { [classes.low]: priorityClass === "low" }
          )}
        >
          <CardHeader
            action={
              <Fragment>
                <IconButton
                  aria-label="More"
                  // aria-owns={menuOpen ? "long-menu" : undefined}
                  aria-haspopup="true"
                  // onClick={this.handleOpenMenuClick}
                  disableRipple
                >
                  <MoreVertIcon />
                </IconButton>
              </Fragment>
            }
            title={this.props.ticket.projectSequence}
            subheader={this.props.ticket.summary}
          />
        </Card>
      </TicketContainer>
    );
  }
}
export default withStyles(styles)(Ticket);
