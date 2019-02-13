import React, { Component } from 'react'

export default class ModalDialog extends Component {
  render() {
    
      if(!this.props.show){
          return null;
      }
    return <div>Hello Modal</div>;
    
  }
}
