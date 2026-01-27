package me.alphamode.beta.proxy.util.data;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.BlockPos;

public class BlockHitResult extends HitResult {
	public static final StreamCodec<ByteBuf, BlockHitResult> CODEC = StreamCodec.ofMember(BlockHitResult::write, BlockHitResult::read);

	private final Direction direction;
	private final BlockPos blockPos;
	private final boolean miss;
	private final boolean inside;
	private final boolean worldBorderHit;

	public static BlockHitResult miss(Vec3d location, Direction direction, BlockPos pos) {
		return new BlockHitResult(true, location, direction, pos, false, false);
	}

	public BlockHitResult(Vec3d location, Direction direction, BlockPos pos, boolean inside) {
		this(false, location, direction, pos, inside, false);
	}

	public BlockHitResult(Vec3d location, Direction direction, BlockPos pos, boolean inside, boolean worldBorderHit) {
		this(false, location, direction, pos, inside, worldBorderHit);
	}

	private BlockHitResult(boolean miss, Vec3d location, Direction direction, BlockPos blockPos, boolean inside, boolean worldBorderHit) {
		super(location);
		this.miss = miss;
		this.direction = direction;
		this.blockPos = blockPos;
		this.inside = inside;
		this.worldBorderHit = worldBorderHit;
	}

	public static BlockHitResult read(final ByteBuf buf) {
		final BlockPos pos = BlockPos.CODEC.decode(buf);
		final Direction face = ModernStreamCodecs.javaEnum(Direction.class).decode(buf);
		final float clickX = buf.readFloat();
		final float clickY = buf.readFloat();
		final float clickZ = buf.readFloat();
		final boolean inside = buf.readBoolean();
		final boolean worldBorder = buf.readBoolean();
		return new BlockHitResult(new Vec3d((double) pos.getX() + clickX, (double) pos.getY() + clickY, (double) pos.getZ() + clickZ), face, pos, inside, worldBorder);
	}

	public void write(final ByteBuf buf) {
		BlockPos blockPos = this.getBlockPos();
		BlockPos.CODEC.encode(buf, blockPos);
		ModernStreamCodecs.javaEnum(Direction.class).encode(buf, this.getDirection());

		final Vec3d location = this.getLocation();
		buf.writeFloat((float) (location.x() - blockPos.getX()));
		buf.writeFloat((float) (location.y() - blockPos.getY()));
		buf.writeFloat((float) (location.z() - blockPos.getZ()));

		buf.writeBoolean(this.isInside());
		buf.writeBoolean(this.isWorldBorderHit());
	}

	public BlockHitResult withDirection(Direction direction) {
		return new BlockHitResult(this.miss, this.location, direction, this.blockPos, this.inside, this.worldBorderHit);
	}

	public BlockHitResult withPosition(BlockPos blockPos) {
		return new BlockHitResult(this.miss, this.location, this.direction, blockPos, this.inside, this.worldBorderHit);
	}

	public BlockHitResult hitBorder() {
		return new BlockHitResult(this.miss, this.location, this.direction, this.blockPos, this.inside, true);
	}

	public BlockPos getBlockPos() {
		return this.blockPos;
	}

	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public HitResult.Type getType() {
		return this.miss ? HitResult.Type.MISS : HitResult.Type.BLOCK;
	}

	public boolean isInside() {
		return this.inside;
	}

	public boolean isWorldBorderHit() {
		return this.worldBorderHit;
	}
}
