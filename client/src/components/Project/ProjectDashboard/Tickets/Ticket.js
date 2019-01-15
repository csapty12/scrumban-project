import React, { Component, Fragment } from "react";
import { Link } from "react-router-dom";
import "../projectDashboard.css";
import { Draggable } from "react-beautiful-dnd";
import styled from "styled-components";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import IconButton from "@material-ui/core/IconButton";
import Dialog from "@material-ui/core/Dialog";
import { withStyles } from "@material-ui/core/styles";
import classNames from "classnames";
import DeleteIcon from "@material-ui/icons/Delete";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import Button from "@material-ui/core/Button";
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
  },
  deletIcon: {
    fontSize: 15
  }
});

class Ticket extends Component {
  constructor(props) {
    super(props);
    this.state = {
      open: false,
      isHovering: false
    };
  }
  handleDelete = ticket => {
    this.handleClose();
    console.log("ticket:" + JSON.stringify(ticket));
    // this.props.deleteTicket(ticket);
  };

  handleClickOpen = () => {
    this.setState({ open: true });
  };

  handleClose = () => {
    this.setState({ open: false });
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
    const priority = ticket.priority;

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
          onMouseEnter={this.handleMouseHover}
          onMouseLeave={this.handleMouseHover}
        >
          <CardHeader
            action={
              <Fragment>
                <IconButton
                  aria-label="Delete"
                  size="small"
                  disableRipple
                  onClick={this.handleClickOpen}
                >
                  {this.state.isHovering && (
                    <DeleteIcon size="small" className={classes.deletIcon} />
                  )}
                </IconButton>
                <Dialog
                  open={this.state.open}
                  onClose={this.handleClose}
                  aria-labelledby="alert-dialog-title"
                  aria-describedby="alert-dialog-description"
                >
                  <DialogTitle id="alert-dialog-title">
                    {"Remove Ticket?"}
                  </DialogTitle>
                  <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                      Are you sure you want to delete:{" "}
                      <b>{this.props.ticket.projectSequence}</b>?
                    </DialogContentText>
                  </DialogContent>
                  <DialogActions>
                    <Button onClick={this.handleClose} color="primary">
                      No
                    </Button>
                    <Button
                      onClick={this.handleDelete.bind(this, ticket)}
                      color="primary"
                      autoFocus
                    >
                      Yes
                    </Button>
                  </DialogActions>
                </Dialog>
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
