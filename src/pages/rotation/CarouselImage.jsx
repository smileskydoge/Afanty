import React from 'react';
import CSSTransitionGroup from 'react-addons-css-transition-group';

/* 轮播图片组件 */
function CarouselImage(props) {
	let {imageSrc, currentIndex, enterDelay, leaveDelay, name, component} = props;

	return (
		<ul className="carousel-image">
			<CSSTransitionGroup
				component={component}
				transitionName={name}
				transitionEnterTimeout={enterDelay}
				transitionLeaveTimeout={leaveDelay}
				className={name}>
				<img 
					alt=""
					src={imageSrc[currentIndex]} 
					key={imageSrc[currentIndex]} 
				/>
			</CSSTransitionGroup>
		</ul>
	);
}	

export default CarouselImage;