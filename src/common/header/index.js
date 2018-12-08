import React from 'react';
import './style.css';
export default class Header extends React.Component {

    constructor(props){
        super(props)
        this.state = {
            nav:['首页','服务','资讯','案例','品质','简介','联系']
        }
    }

    handleNavClick = (index) => {
        this.props.handleNav(index)
    }

    render(){
        return(<div className="header-view">
            <span className="company-name">AFANTY</span>
            <ul className="head-ul">
                {this.state.nav.map((item,index)=>{
                    return(<li key={index} className={this.props.navIndex===index ? "head-li-active":"head-li"} onClick={()=>this.handleNavClick(index)}>{item}</li>)
                })}
            </ul>
        </div>)
    }
}