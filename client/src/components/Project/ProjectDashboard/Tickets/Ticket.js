import React, { Component, Fragment } from "react";
import "../projectDashboard.css";
import { Draggable } from "react-beautiful-dnd";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import IconButton from "@material-ui/core/IconButton";
import Dialog from "@material-ui/core/Dialog";
import { withStyles } from "@material-ui/core/styles";
import classNames from "classnames";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import Button from "@material-ui/core/Button";
import ClearIcon from "@material-ui/icons/Clear";
import Slide from "@material-ui/core/Slide";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import CloseIcon from "@material-ui/icons/Close";

const styles = theme => ({
  cards: {
    marginTop: 5,
    marginBottom: 5,
    borderRadius: 1
  },
  ticket: {
    backgroundColor: "#F0F2EA"
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
  },

  appBar: {
    position: "relative"
  },
  flex: {
    flex: 1
  }
});

function Transition(props) {
  return <Slide direction="up" {...props} />;
}

class Ticket extends Component {
  constructor(props) {
    super(props);
    this.state = {
      open: false,
      isHovering: false,
      openTicketDetails: false,
      isDragDisabled: false
    };
  }
  handleDelete = ticket => {
    this.handleClose();

    ticket.swimLane = this.props.swimLaneId;
    this.props.removeTicket(ticket);
  };

  handleClickOpen = event => {
    event.stopPropagation();
    this.setState({ open: true });
  };

  handleClose = () => {
    this.setState({ open: false });
  };

  handleOpenTicketDetails = () => {
    this.setState({ openTicketDetails: true, isDragDisabled: true });
  };

  handleCloseTicketDetails = () => {
    this.setState({ openTicketDetails: false, isDragDisabled: false });
  };

  deleteButton = () => {
    const { classes, ticket } = this.props;
    return (
      <Fragment>
        <IconButton
          aria-label="Delete"
          size="small"
          disableRipple
          onClick={this.handleClickOpen}
        >
          <ClearIcon size="small" className={classes.deletIcon} />
        </IconButton>
        <Dialog
          open={this.state.open}
          onClose={this.handleClose}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description"
        >
          <DialogTitle id="alert-dialog-title">{"Remove Ticket?"}</DialogTitle>
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
    );
  };

  openProjectDetailsDialog = () => {
    const { classes, ticket } = this.props;
    return (
      <Fragment>
        <span onClick={this.handleOpenTicketDetails}>
          {ticket.projectSequence}
        </span>
        <Dialog
          fullScreen
          open={this.state.openTicketDetails}
          onClose={this.handleCloseTicketDetails}
          TransitionComponent={Transition}
        >
          <AppBar className={classes.appBar}>
            <Toolbar>
              <IconButton
                color="inherit"
                onClick={this.handleCloseTicketDetails}
                aria-label="Close"
              >
                <CloseIcon />
              </IconButton>
              <Typography variant="h6" color="inherit" className={classes.flex}>
                {ticket.projectIdentifier} / {ticket.projectSequence}
              </Typography>
            </Toolbar>
          </AppBar>
          <DialogContent>
            <DialogContentText id="alert-dialog-description">
              Let Google help apps determine location. This means sending
              anonymous location data to Google, even when no apps are running.
            </DialogContentText>
          </DialogContent>
        </Dialog>
      </Fragment>
    );
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
      <Draggable
        index={this.props.index}
        draggableId={this.props.ticket.projectSequence}
        isDragDisabled={this.state.isDragDisabled}
      >
        {(provided, snapshot) => (
          <div
            {...provided.draggableProps}
            {...provided.dragHandleProps}
            ref={provided.innerRef}
            {...provided.droppableProps}
          >
            <Card
              className={classNames(
                classes.cards,
                { [classes.high]: priorityClass === "high" },
                { [classes.medium]: priorityClass === "medium" },
                { [classes.low]: priorityClass === "low" }
              )}
              style={{
                backgroundColor: snapshot.isDragging ? "#F0F2EA" : null
              }}
            >
              <CardHeader
                title={this.openProjectDetailsDialog()}
                subheader={this.props.ticket.summary}
                action={this.deleteButton()}
              />
            </Card>
          </div>
        )}
      </Draggable>
    );
  }
}
export default withStyles(styles)(Ticket);
