import React, { Component, Fragment } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import Ticket from "./Tickets/Ticket";
class Backlog extends Component {
  constructor(props) {
    super(props);
    this.state = {
      allTickets: [],
      columns: [],
      projectIdentifier: props.projectIdentifier,
      columnOrder: [],
      errors: {}
    };
  }

  componentDidMount() {
    axios
      .get(`http://localhost:8080/api/backlog/${this.state.projectIdentifier}`)
      .then(json => {
        console.log("json for ticket: " + JSON.stringify(json));
        this.setState({
          allTickets: json.data.tasks,
          columns: json.data.columns,
          columnOrder: json.data.columnOrder
        });
      })
      .catch(json => {
        this.setState({
          errors: {
            projectIdentifier: json.response.data.projectIdentifier
          }
        });
      });
  }

  handleTicketDelete = project => {
    const backlogTickets = this.state.backlog;
    const { projectIdentifier, projectSequence } = project;
    if (
      window.confirm(
        `Are you sure you want to delete ticket: ${projectSequence}`
      )
    ) {
      axios
        .delete(
          `http://localhost:8080/api/backlog/${projectIdentifier}/${projectSequence}`
        )
        .then(() => {
          let filteredTickets = backlogTickets.filter(
            item => item.projectSequence !== projectSequence
          );
          this.setState({ backlog: filteredTickets });
        });
    }
  };

  render() {
    let columnData = {};

    if (this.state.columnOrder.length > 0) {
      this.state.columnOrder.forEach(columnId => {
        columnData[columnId] = null;
      });
      this.state.columns.forEach((column, index) => {
        columnData[this.state.columnOrder[index]] =
          column[this.state.columnOrder[index]];
      });
      console.log("column data: " + JSON.stringify(columnData));
      return this.state.columnOrder.map(columnId => {
        const column = columnData[columnId];
        console.log(column.title);
        return column.title;
      });
    } else {
      return null;
    }
    return <Fragment />;
  }
}

export default Backlog;
