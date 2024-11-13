export default function PremiumPage() {
  return (
    <>
      <div className="absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2">
        <div className="flex justify-between">
          <div className="inlline 1/4 bg-surface-0 rounded-xl mx-2 text-center p-2">
            <p className="text-3xl font-semibold text-text">Basic plan</p>
            <p className="text-subtext text-xl">- amogus</p>
            <p className="text-subtext text-xl">- impostors</p>
            <p className="text-subtext text-xl">- testicles</p>
            <p className="text-subtext text-xl">- should</p>
            <p className="text-subtext text-xl mb-2">- okarun</p>
            <buton className="bg-white bg-opacity-5 p-2 rounded-xl text-subtext">
              Current plan
            </buton>
          </div>
          <div className="inline 1/4 mx-2">
            <div className="inlline 1/4 bg-green-1 rounded-xl mx-2 text-center p-2">
              <p className="text-3xl font-semibold text-surface-1">
                Premium plan
              </p>
              <p className="text-surface-1 text-xl">- NO amogus</p>
              <p className="text-surface-1 text-xl">- NO impostors</p>
              <p className="text-surface-1 text-xl">- COOL testicles</p>
              <p className="text-surface-1 text-xl">- NO should</p>
              <p className="text-surface-1 text-xl mb-2">- okarun</p>
              <buton className="bg-surface-1  p-2 rounded-xl text-text">
                BUY!!!
              </buton>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
