package coffee.mort.steambly.tileentity;

public class CreativeGeneratorTileEntity extends SteamTileEntity {

	@Override
	public int getSteamVolume() {
		return 200;
	}

	@Override
	public void onServerUpdate() {
		addSteam(1);
		super.onServerUpdate();
	}
}
