import React from "react";
import { useAuth } from "../context/AuthContext";

export default function HomePage() {
  const { isAuthenticated } = useAuth();

  return (
    <>
      <div className="flex p-2">
        <div className="w-1/2 p-2">
          <div className="bg-white bg-opacity-5 rounded-xl">
            <p className="text-text text-3xl p-2">Just Bank</p>
            <p className="p-2 text-subtext text-lg">
              Lorem ipsum dolor sit amet, consectetur adipiscing elit.
              Vestibulum efficitur pretium felis, sed hendrerit mauris imperdiet
              et. Pellentesque bibendum fermentum laoreet. Praesent at velit
              suscipit, elementum lorem nec, venenatis risus. Vivamus quis
              euismod nibh. In at tristique elit, sed accumsan orci. In feugiat
              tincidunt sodales. Class aptent taciti sociosqu ad litora torquent
              per conubia nostra, per inceptos himenaeos. Proin sed augue quis
              orci elementum suscipit nec at nulla. Morbi elementum odio massa,
              in vulputate quam efficitur ac. Nulla dignissim, sapien facilisis
              faucibus bibendum, sem sem tincidunt mi, ac pharetra quam mi
              scelerisque mauris. Etiam posuere tincidunt nisi eu blandit. Nam
              et risus elit. Sed sed sodales risus, vel ornare ante. Praesent
              finibus lorem non magna laoreet lobortis. Donec viverra facilisis
              justo, non feugiat nisl lobortis a. Etiam vel nulla vitae ante
              condimentum luctus at eget mauris. Quisque tincidunt dui non
              finibus tempus. Cras malesuada ipsum non purus egestas congue.
              Praesent commodo dolor in urna ornare faucibus. Morbi lobortis
              vitae arcu sit amet gravida. Praesent ut laoreet arcu. Praesent
              congue, libero ac blandit ornare, purus lorem porttitor ipsum, in
              posuere ex sapien nec ante.
            </p>
          </div>
        </div>
        <div className="w-1/2">
          <div className="flex p-2">
            <div className="w-1/2 bg-white bg-opacity-5 rounded-xl mr-2">
              <p className="p-2 text-text text-xl">Some information</p>
              <p className="p-2 text-subtext text-lg">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                Vestibulum efficitur pretium felis, sed hendrerit mauris
                imperdiet et.
              </p>
            </div>
            <div className="w-1/2 bg-white bg-opacity-5 rounded-xl">
              <p className="p-2 text-text text-xl">Another info block</p>
              <p className="px-2 text-subtext text-lg">- first option</p>
              <p className="px-2 text-subtext text-lg">- second option</p>
              <p className="px-2 text-subtext text-lg">- third option</p>
            </div>
          </div>
          {!isAuthenticated && (
            <div className="bg-white bg-opacity-5 rounded-xl p-2 mr-2 ml-2">
              <p className="text-center text-3xl text-text">Have account?</p>
              <div className="text-center">
                <button className="py-2 px-4 bg-green-1 rounded-full transition-all hover:bg-white hover:text-base">
                  Login
                </button>
                <p className="inline text-xl text-subtext px-4">or</p>
                <button className="py-2 px-4 bg-white bg-opacity-5 text-subtext rounded-full transition-all hover:bg-white hover:text-base">
                  Register
                </button>
              </div>
            </div>
          )}
          <div className="p-2">
            <div className=" bg-white bg-opacity-5 rounded-xl p-2 mr-2 ml-2">
              <img
                src="https://d1fufvy4xao6k9.cloudfront.net/images/blog/posts/2022/11/imagetools6.jpg"
                className=" rounded-xl"
              />
              <p className="text-3xl text-text font-semibold mt-2 border-t-2 border-green-1">
                Dude Personson
              </p>
              <p className="text-xl text-subtext">CEO of JustBank Company</p>
              <p className="text-lg text-subtext">
                Praesent ut laoreet arcu. Praesent congue, libero ac blandit
                ornare, purus lorem porttitor ipsum, in posuere ex sapien nec
                ante.
              </p>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
