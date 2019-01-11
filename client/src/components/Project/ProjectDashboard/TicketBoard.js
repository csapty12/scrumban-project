import React, { Component } from "react";
// import { Link } from "react-router-dom";
import axios from "axios";
import Column from "./Column";
export default class Backlog extends Component {
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
      .get(`http://localhost:8080/dashboard/${this.state.projectIdentifier}`)
      .then(json => {
        console.log("json response: " + JSON.stringify(json));
        this.setState({
          allTickets: json.data.tasks,
          columns: json.data.columns,
          columnOrder: json.data.columnOrder
        });
      });
    // .catch(json => {
    //   this.setState({
    //     errors: {
    //       projectIdentifier: json.response.data.projectIdentifier
    //     }
    //   });
    // });
  }

  // handleTicketDelete = project => {
  //   const backlogTickets = this.state.backlog;
  //   const { projectIdentifier, projectSequence } = project;
  //   if (
  //     window.confirm(
  //       `Are you sure you want to delete ticket: ${projectSequence}`
  //     )
  //   ) {
  //     axios
  //       .delete(
  //         `http://localhost:8080/api/backlog/${projectIdentifier}/${projectSequence}`
  //       )
  //       .then(() => {
  //         let filteredTickets = backlogTickets.filter(
  //           item => item.projectSequence !== projectSequence
  //         );
  //         this.setState({ backlog: filteredTickets });
  //       });
  //   }
  // };

  // persistTicketToNewColumn = ticketAfterMove => {
  //   console.log("udpated ticket: " + JSON.stringify(ticketAfterMove));
  //   console.log("project identifier: " + this.state.projectIdentifier);
  //   axios.post(
  //     `http://localhost:8080/api/backlog/${this.state.projectIdentifier}`,
  //     ticketAfterMove
  //   );
  // };

  render() {
    // console.log("this.allTickets: " + JSON.stringify(this.state.allTickets));
    // console.log("this.columns" + JSON.stringify(this.state.columns));
    // console.log("this.columnOrder" + JSON.stringify(this.state.columnOrder));

    return (
      <div className="container-fluid">
        <section className="card-horizontal-scrollable-container">
          {/* {this.state.columnOrder.map((columnId, index) => {
            const column = this.state.columns[index][columnId];
            const tasks = column.taskIds.map(
              (taskId, index) => this.state.allTickets[0][taskId]
            );
            // console.log("tasks: " + JSON.stringify(tasks));
            return <Column key={column.id} column={column} tasks={tasks} />;
          })} */}
        </section>
      </div>
    );
  }
}
