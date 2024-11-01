export default function Dashboard() {
  return (
    <>
      <div className="m-4">
        <div className="flex">
          {/* LEFT SEGMENT */}
          <div className="w-1/3 border border-gray-500 p-2">
            <div className="p-2">
              <p className="text-subtext font-light text-lg">Balance:</p>
              <h1 className="text-text text-6xl text-opacity-80 font-medium">
                $560.30
              </h1>
            </div>
            <div className="mt-4 p-2">
              <h1 className="font-light text-xl text-subtext">
                Monthly total:
              </h1>
              <div className="p-2">
                <div className="py-2 flex justify-between text-subtext border-b border-gray-500">
                  <p className="text-xl inline">Income:</p>
                  <p className="text-3xl inline ml-auto">$2.890</p>
                </div>
                <div className="py-2 flex justify-between text-subtext">
                  <p className="text-xl inline">Spending:</p>
                  <p className="text-3xl inline ml-auto">$1.350</p>
                </div>
              </div>
            </div>
            <div className="bg-surface-0 rounded-xl"></div>
          </div>
          {/* MIDDLE SEGMENT */}
          <div className="w-1/3 border border-gray-500">
            <p>Seccond segment</p>
          </div>
          {/* RIGHT SEGMENT */}
          <div className="w-1/3 border border-gray-500">
            <p>Third segment</p>
          </div>
        </div>
      </div>
    </>
  );
}
