import React from 'react';
import Header from './common/header'
import Rotation from './pages/rotation'
import Service from './pages/service';
import News from './pages/news'
import Case from './pages/cases'
import Quality from './pages/quality'
import Synopsis from './pages/synopsis'
import Contact from './pages/contact';

const pageArray = [<Rotation/>,<Service/>,<News/>,<Case/>,<Quality/>,<Synopsis/>,<Contact/>]

export default class App extends React.Component {

    constructor(props){
        super(props)
        this.state = {
            navIndex:0
        }
    }

    handleWheel = (event) => {

        let data = event.deltaY;
        let nowNavIndex = this.state.navIndex;

        if(data>0){
         (++nowNavIndex) < 7 && this.setState((prevState)=>({navIndex:++prevState.navIndex}))
        }else{
         (--nowNavIndex) > -1 && this.setState((prevState)=>({navIndex:--prevState.navIndex}))
        }
    }


    handleNav = (index) => {
        this.setState({navIndex:index})
    }

    render(){
        return(
        <div onWheel={this.handleWheel}>
            <Header handleNav={this.handleNav} navIndex={this.state.navIndex}/>
            {pageArray[this.state.navIndex]}
        </div>
        )
    }
}